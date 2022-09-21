package com.moises.todo.todorestapi.api.controller;

import com.moises.todo.todorestapi.api.converter.TodoListConverter;
import com.moises.todo.todorestapi.api.dto.TodoListDto;
import com.moises.todo.todorestapi.api.dto.input.TodoListInputDto;
import com.moises.todo.todorestapi.api.dto.view.TodoListBasicViewDto;
import com.moises.todo.todorestapi.domain.exception.BusinessException;
import com.moises.todo.todorestapi.domain.exception.TodoListNotFoundException;
import com.moises.todo.todorestapi.domain.model.TodoList;
import com.moises.todo.todorestapi.domain.repository.TodoListRepo;
import com.moises.todo.todorestapi.domain.service.TodoListService;
import com.moises.todo.todorestapi.domain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/todo-lists/{userCode}")
public class TodoListController {

    @Autowired
    private TodoListService todoListService;

    @Autowired
    private TodoListRepo todoListRepo;

    @Autowired
    private TodoListConverter todoListConverter;

    @Autowired
    private UserService userService;

    @GetMapping
    public List<TodoListBasicViewDto> list(@PathVariable String userCode) {

        userService.findOrFail(userCode);

        return todoListConverter.toCollectionWithTodoListBasicInfoDtoList(todoListRepo.findAll());

    }

    @GetMapping("/{todoListId}")
    public TodoListBasicViewDto getById(@PathVariable String userCode, @PathVariable Long todoListId) {

        userService.findOrFail(userCode);

        TodoList todoList = todoListService.findOrFail(todoListId);

       return todoListConverter.toTodoListBasicInfoDto(todoList);

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TodoListDto add(@PathVariable String userCode, @RequestBody TodoListInputDto todoListInputDto) {

        userService.findOrFail(userCode);

        TodoList todoList = todoListConverter.toEntity(todoListInputDto);
        todoListService.save(userCode, todoList);

        return todoListConverter.toDto(todoList);

    }

    @PutMapping("/{todoListId}")
    public TodoListDto update(@PathVariable String userCode, @PathVariable Long todoListId,
                              @RequestBody @Valid TodoListInputDto todoListInputDto) {

        userService.findOrFail(userCode);

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
    public void delete(@PathVariable String userCode, @PathVariable Long todoListId) {

        userService.findOrFail(userCode);

        todoListService.delete(todoListId);

    }


}
