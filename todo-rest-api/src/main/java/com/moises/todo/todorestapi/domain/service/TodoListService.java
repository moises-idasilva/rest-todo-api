/*
 * Copyright (c) 2022.
 * @Author: Moises I da Silva
 * Email: dev.moises.dasilva@gmail.com
 */

package com.moises.todo.todorestapi.domain.service;

import com.moises.todo.todorestapi.domain.exception.EntityInUseException;
import com.moises.todo.todorestapi.domain.exception.TodoListNotFoundException;
import com.moises.todo.todorestapi.domain.exception.UserNotFoundException;
import com.moises.todo.todorestapi.domain.model.TodoDirectory;
import com.moises.todo.todorestapi.domain.model.TodoList;
import com.moises.todo.todorestapi.domain.model.User;
import com.moises.todo.todorestapi.domain.repository.TodoListRepo;
import com.moises.todo.todorestapi.domain.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class TodoListService {

    public static final String MSG_ENTITY_IN_USE = "ToDo List with ID %d can't be deleted. " +
            "Reason: Other entity is using this entity.";

    @Autowired
    private TodoListRepo todoListRepo;

    @Autowired
    private TodoDirectoryService todoDirectoryService;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private SendEmailService sendEmail;

    @Transactional
    public TodoList save(String todoDirectoryOwnerCode, TodoList todoList) {

        TodoDirectory todoDirectory = todoDirectoryService.findOrFail(todoDirectoryOwnerCode);

        todoList.setTodoDirectory(todoDirectory);

        if (todoList.getCompleted() == null) {
            todoList.setCompleted(false);
        }

        return todoListRepo.save(todoList);

    }

    @Transactional
    public TodoList update(TodoList todoList) {

        return todoListRepo.save(todoList);

    }

    @Transactional
    public void delete(Long todoListId) {

        findOrFail(todoListId);

        try {
            todoListRepo.deleteTodoListById(todoListId);
            todoListRepo.flush();

        } catch (EmptyResultDataAccessException e) {
            throw new TodoListNotFoundException(todoListId);

        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(
                    String.format(MSG_ENTITY_IN_USE, todoListId));
        }

    }

    public TodoList findOrFail(Long todoListId) {

        return todoListRepo.findTodoListById(todoListId).orElseThrow(() -> new TodoListNotFoundException(todoListId));

    }

    public void setAsCompleted(Long todoListId) {

        TodoList todoListPresent = findOrFail(todoListId);

        todoListPresent.isCompleted();

        update(todoListPresent);

    }

    public void setAsNotCompleted(Long todoListId) {

        TodoList todoListPresent = findOrFail(todoListId);

        todoListPresent.isNotCompleted();

        update(todoListPresent);

    }

    public void sendEmailWithToDoListLoadedWithToDoItems(String userCode, Long todoListId) {

        Optional<User> user = userRepo.findByUserCode(userCode);

        TodoList todoList = findOrFail(todoListId);

        var message = SendEmailService.Message.builder()
                .subject("Your ToDo List")
                .body("list.html")
                .variable("todoList", todoList)
                .variable("userFullName", user.get().getFirstName() + " " + user.get().getLastName())
//                .variable("todoListName", todoList.getName())
//                .variable("todoListDescription", todoList.getDescription())
                //   .variable("todoItems", todoList.getTodoItemList())
                .recipient(user.get().getEmail())
                .build();

        sendEmail.send(message);

    }


}
