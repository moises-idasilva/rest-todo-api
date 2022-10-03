/*
 * Copyright (c) 2022.
 * @Author: Moises I da Silva
 * Email: dev.moises.dasilva@gmail.com
 */

package com.moises.todo.todorestapi.domain.service;

import com.moises.todo.todorestapi.domain.exception.BusinessException;
import com.moises.todo.todorestapi.domain.exception.EntityInUseException;
import com.moises.todo.todorestapi.domain.exception.UserNotFoundException;
import com.moises.todo.todorestapi.domain.model.TodoDirectory;
import com.moises.todo.todorestapi.domain.model.User;
import com.moises.todo.todorestapi.domain.repository.TodoDirectoryRepo;
import com.moises.todo.todorestapi.domain.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {

    public static final String MSG_ENTITY_IN_USE = "User with ID %d can't be deleted. " +
            "Reason: Other entity is using this entity.";

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private TodoDirectoryRepo toDoDirectoryRepo;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Transactional
    public User save(User user) {

        validateIfUsernameAlreadyExist(user);
        validateIfEmailAlreadyExist(user);

        User provisoryUser = userRepo.save(user);

        if (provisoryUser.getTodoDirectory() == null) {
            TodoDirectory toDoDirectory = new TodoDirectory();
            toDoDirectory.setOwnerUsername(provisoryUser.getUsername());
            toDoDirectory.setOwnerCode(provisoryUser.getUserCode());
            toDoDirectoryRepo.save(toDoDirectory);

            provisoryUser.setTodoDirectory(toDoDirectory);
        }

        provisoryUser.setPassword(encodePassword(provisoryUser));

        return userRepo.save(provisoryUser);

    }

    @Transactional
    public User update(User user) {

        return userRepo.save(user);

    }

    public void delete(String userCode) {

        try {
            userRepo.deleteByUserCode(userCode);
            userRepo.flush();

        } catch (EmptyResultDataAccessException e) {
            throw new UserNotFoundException(userCode);

        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(
                    String.format(MSG_ENTITY_IN_USE, userCode));
        }

    }


    private void validateIfUsernameAlreadyExist(User user) {
        Optional<User> existentUsername = userRepo.findByUsernameIgnoreCase(user.getUsername());

        if (existentUsername.isPresent()) {
            throw new BusinessException(
                    String.format("There is already an registered user using the username '%s'.", user.getUsername())
            );
        }
    }

    private void validateIfEmailAlreadyExist(User user) {
        Optional<User> existentEmail = userRepo.findByEmail(user.getEmail());

        if (existentEmail.isPresent()) {
            throw new BusinessException(
                    String.format("There is already an registered user using the email '%s'.", user.getEmail())
            );
        }
    }

    public User findOrFail(String userCode) {
        return userRepo.findByUserCode(userCode)
                .orElseThrow(() -> new UserNotFoundException(userCode));
    }

    @Transactional
    public void changePassword(String userCode, String currentPassword, String newPassword) {

        User user = findOrFail(userCode);

        if (user.passwordDoesNotMatch(currentPassword)) {
            throw new BusinessException(
                    String.format("The entered password: %s does not match with the password associated with the " +
                            "username: %s", currentPassword, user.getUsername())
            );
        }

        user.setPassword(newPassword);

    }

    public void activateAccount(String userCode, String activationCode) throws Exception {

        try {
            Optional<User> user = userRepo.findByUserCode(userCode);

            if (Objects.equals(user.get().getActivationCode(), activationCode)) {

                user.get().activateAccount();

                User activeUser = user.get();

                update(activeUser);

            } else {
                throw new Exception("The activation code that was informed does not match the user records. " +
                        "Please check if the activation code you entered match with the one that was sent to " +
                        user.get().getEmail() + " and try again.");
            }

        } catch (EmptyResultDataAccessException e) {
            throw new UserNotFoundException(userCode);

        }

    }

    private String encodePassword(User user) {
        return passwordEncoder.encode(user.getPassword());
    }


}
