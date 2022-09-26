/*
 * Copyright (c) 2022.
 * @Author: Moises I da Silva
 * Email: dev.moises.dasilva@gmail.com
 */

package com.moises.todo.todorestapi.api.controller;

import com.moises.todo.todorestapi.api.converter.TodoListConverter;
import com.moises.todo.todorestapi.api.dto.TodoListDto;
import com.moises.todo.todorestapi.api.dto.input.TodoListInputDto;
import com.moises.todo.todorestapi.api.dto.view.TodoListBasicViewDto;
import com.moises.todo.todorestapi.domain.exception.BusinessException;
import com.moises.todo.todorestapi.domain.exception.TodoListNotFoundException;
import com.moises.todo.todorestapi.domain.model.TodoList;
import com.moises.todo.todorestapi.domain.repository.TodoListRepo;
import com.moises.todo.todorestapi.domain.service.TodoDirectoryService;
import com.moises.todo.todorestapi.domain.service.TodoListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/todo-lists")
public class TodoListController {

    @Autowired
    private TodoListService todoListService;

    @Autowired
    private TodoListRepo todoListRepo;

    @Autowired
    private TodoListConverter todoListConverter;

    @Autowired
    private TodoDirectoryService todoDirectoryService;

    @GetMapping
    public List<TodoListBasicViewDto> list(@RequestParam String userCode) {

        todoDirectoryService.findOrFail(userCode);

        return todoListConverter.toCollectionWithTodoListBasicInfoDtoList(todoListRepo.findAll());

    }

    @GetMapping(path = "/{todoListId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public TodoListBasicViewDto getById(@RequestParam String userCode, @PathVariable Long todoListId) {

        todoDirectoryService.findOrFail(userCode);

        TodoList todoList = todoListService.findOrFail(todoListId);

       return todoListConverter.toTodoListBasicInfoDto(todoList);

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TodoListDto add(@RequestParam String userCode, @RequestBody TodoListInputDto todoListInputDto) {

        todoDirectoryService.findOrFail(userCode);

        TodoList todoList = todoListConverter.toEntity(todoListInputDto);
        todoListService.save(userCode, todoList);

        return todoListConverter.toDto(todoList);

    }

    @PutMapping("/{todoListId}")
    public TodoListDto update(@RequestParam String userCode, @PathVariable Long todoListId,
                              @RequestBody @Valid TodoListInputDto todoListInputDto) {

        todoDirectoryService.findOrFail(userCode);

        try {
            TodoList todoListPresent = todoListService.findOrFail(todoListId);
            todoListConverter.copyToEntity(todoListInputDto, todoListPresent);

            return todoListConverter.toDto(todoListService.update(todoListPresent));

        } catch (TodoListNotFoundException e) {
            throw new BusinessException(e.getMessage());
        }

    }

    @DeleteMapping("/{todoListId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@RequestParam String userCode, @PathVariable Long todoListId) {

        todoDirectoryService.findOrFail(userCode);

        todoListService.delete(todoListId);

    }

    @PutMapping("/{todoListId}/completed")
    public void setAsCompleted(@PathVariable Long todoListId) {

        todoListService.setAsCompleted(todoListId);

    }

    @PutMapping("/{todoListId}/not-completed")
    public void setAsNotCompleted(@PathVariable Long todoListId) {

        todoListService.setAsNotCompleted(todoListId);

    }

    @PostMapping("/send-todo-list-to -email/{todoListId}")
    @ResponseStatus(HttpStatus.OK)
    public void sendTodoListToUserEmail(@RequestParam String userCode, @PathVariable Long todoListId) {

        todoListService.sendEmailWithToDoListLoadedWithToDoItems(userCode, todoListId);

    }


}
