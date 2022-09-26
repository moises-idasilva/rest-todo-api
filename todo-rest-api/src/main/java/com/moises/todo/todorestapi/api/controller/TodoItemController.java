/*
 * Copyright (c) 2022.
 * @Author: Moises I da Silva
 * Email: dev.moises.dasilva@gmail.com
 */

package com.moises.todo.todorestapi.api.controller;

import com.moises.todo.todorestapi.api.converter.TodoItemConverter;
import com.moises.todo.todorestapi.api.dto.TodoItemDto;
import com.moises.todo.todorestapi.api.dto.input.TodoItemInputDto;
import com.moises.todo.todorestapi.api.dto.view.TodoItemBasicViewDto;
import com.moises.todo.todorestapi.domain.exception.BusinessException;
import com.moises.todo.todorestapi.domain.exception.TodoItemNotFoundException;
import com.moises.todo.todorestapi.domain.model.TodoItem;
import com.moises.todo.todorestapi.domain.repository.TodoItemRepo;
import com.moises.todo.todorestapi.domain.service.TodoItemService;
import com.moises.todo.todorestapi.domain.service.TodoListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/todo-items")
public class TodoItemController {

    @Autowired
    private TodoItemRepo todoItemRepo;

    @Autowired
    private TodoItemService todoItemService;

    @Autowired
    private TodoItemConverter todoItemConverter;

    @Autowired
    private TodoListService todoListService;

    @GetMapping
    public List<TodoItemBasicViewDto> list(){

        return todoItemConverter.todoItemBasicViewDtoList(todoItemRepo.findAll());

    }

    @GetMapping("/{todoItemId}")
    public TodoItemDto getById(@PathVariable Long todoItemId) {

        TodoItem todoItem = todoItemService.findOrFail(todoItemId);

        return todoItemConverter.toDto(todoItem);

    }

    @PostMapping
    public TodoItemDto save(@RequestParam Long todoListId, @RequestBody @Valid TodoItemInputDto todoItemInputDto) {

        todoListService.findOrFail(todoListId);

        TodoItem todoItem = todoItemConverter.toEntity(todoItemInputDto);
        todoItemService.save(todoListId, todoItem);

        return todoItemConverter.toDto(todoItem);

    }

    @PutMapping("/{todoItemId}")
    public TodoItemDto update(@PathVariable Long todoItemId, @RequestBody @Valid TodoItemInputDto todoItemInputDto) {

        try {
            TodoItem todoItemPresent = todoItemService.findOrFail(todoItemId);
            todoItemConverter.copyToEntity(todoItemInputDto, todoItemPresent);

            return todoItemConverter.toDto(todoItemService.update(todoItemPresent));

        } catch (TodoItemNotFoundException e) {
            throw new BusinessException(e.getMessage());
        }

    }

    @PutMapping("/{todoItemId}/completed")
    public void setAsCompleted(@PathVariable Long todoItemId) {

            todoItemService.setAsCompleted(todoItemId);

    }

    @PutMapping("/{todoItemId}/not-completed")
    public void setAsNotCompleted(@PathVariable Long todoItemId) {

        todoItemService.setAsNotCompleted(todoItemId);

    }

    @DeleteMapping("/{todoItemId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long todoItemId) {

        todoItemService.delete(todoItemId);

    }

}
