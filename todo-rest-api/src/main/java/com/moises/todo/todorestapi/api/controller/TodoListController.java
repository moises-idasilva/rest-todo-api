package com.moises.todo.todorestapi.api.controller;

import com.moises.todo.todorestapi.api.converter.TodoListConverter;
import com.moises.todo.todorestapi.api.dto.TodoListDto;
import com.moises.todo.todorestapi.api.dto.input.TodoListInputDto;
import com.moises.todo.todorestapi.api.dto.view.TodoListBasicViewDto;
import com.moises.todo.todorestapi.domain.model.TodoList;
import com.moises.todo.todorestapi.domain.repository.TodoListRepo;
import com.moises.todo.todorestapi.domain.service.TodoListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping
    public List<TodoListBasicViewDto> list() {

        return todoListConverter.toCollectionWithTodoListBasicInfoDtoList(todoListRepo.findAll());

    }

    @GetMapping("/{todoListId}")
    public TodoListBasicViewDto getById(@PathVariable Long todoListId) {

       TodoList todoList = todoListService.findOrFail(todoListId);

       return todoListConverter.toTodoBasicInfoDto(todoList);

    }

    @PostMapping("/{userCode}")
    @ResponseStatus(HttpStatus.CREATED)
    public TodoListDto add(@PathVariable String userCode, @RequestBody TodoListInputDto todoListInputDto) {

        TodoList todoList = todoListConverter.toEntity(todoListInputDto);
        todoListService.save(userCode, todoList);

        return todoListConverter.toDto(todoList);

    }


}
