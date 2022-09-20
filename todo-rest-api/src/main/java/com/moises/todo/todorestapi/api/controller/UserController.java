package com.moises.todo.todorestapi.api.controller;

import com.moises.todo.todorestapi.api.converter.TodoConverter;
import com.moises.todo.todorestapi.api.converter.UserConverter;
import com.moises.todo.todorestapi.api.dto.TodoDirectoryDto;
import com.moises.todo.todorestapi.api.dto.UserDto;
import com.moises.todo.todorestapi.api.dto.input.UserInputDto;
import com.moises.todo.todorestapi.api.dto.input.UserWithPasswordInputDto;
import com.moises.todo.todorestapi.api.dto.view.UserBasicInfoViewDto;
import com.moises.todo.todorestapi.domain.exception.UserNotFoundException;
import com.moises.todo.todorestapi.domain.model.TodoDirectory;
import com.moises.todo.todorestapi.domain.model.User;
import com.moises.todo.todorestapi.domain.repository.UserRepo;
import com.moises.todo.todorestapi.domain.service.TodoDirectoryService;
import com.moises.todo.todorestapi.domain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private UserService userService;

    @Autowired
    private UserConverter userConverter;

    @Autowired
    private TodoDirectoryService todoDirectoryService;

    @Autowired
    private TodoConverter todoConverter;

    @GetMapping("/{userCode}")
    public UserBasicInfoViewDto find(@PathVariable String userCode) {

        User user = userService.findOrFail(userCode);

        return userConverter.toUserBasicInfoDto(user);

    }

    @GetMapping("/find-by-username")
    public UserBasicInfoViewDto findBasicUserInfoByUsername(@RequestParam String username) {

        Optional<User> user = userRepo.findByUsernameIgnoreCase(username);

        return userConverter.toUserBasicInfoDto(user.orElseThrow(() -> new UserNotFoundException(
                String.format("There is no user with the username %s", username)
        )));

    }

    @GetMapping
    public List<UserBasicInfoViewDto> list() {

        return userConverter.toCollectionWithBasicUserInfoDTO(userRepo.findAll());

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto add(@RequestBody UserWithPasswordInputDto userWithPasswordInputDto) {

        User user = userConverter.toEntity(userWithPasswordInputDto);
        user = userService.save(user);

        return userConverter.toDto(user);

    }

    @PutMapping("/{userCode}")
    public UserDto update(@PathVariable String userCode, @RequestBody UserInputDto userInputDto) {

        TodoDirectory todoDirectoryTemp = todoDirectoryService.findOrFail(userCode);

        TodoDirectoryDto todoDirectoryInput = todoConverter.toDto(todoDirectoryTemp);

        TodoDirectory todoDirectoryPresent = todoDirectoryService.findOrFail(userCode);
        todoConverter.copyToEntity(todoDirectoryInput, todoDirectoryPresent);
        todoDirectoryService.update(todoDirectoryPresent);

        User userPresent = userService.findOrFail(userCode);
        userPresent.setTodoDirectory(todoDirectoryPresent);
        userConverter.copyToEntity(userInputDto, userPresent);

        userPresent.setTodoDirectory(todoDirectoryPresent);

        userPresent = userService.update(userPresent);

        return userConverter.toDto(userPresent);

    }

}
