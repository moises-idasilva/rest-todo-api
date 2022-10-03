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
import com.moises.todo.todorestapi.domain.service.ToDoListPdfService;
import com.moises.todo.todorestapi.domain.service.TodoItemService;
import com.moises.todo.todorestapi.domain.service.TodoListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

    @Autowired
    private ToDoListPdfService toDoListPdfService;

    @GetMapping
    public List<TodoItemBasicViewDto> list(){

        return todoItemConverter.todoItemBasicViewDtoList(todoItemRepo.findAll());

    }

    @GetMapping("/{todoItemId}")
    public TodoItemDto getById(@PathVariable Long todoItemId) {

        TodoItem todoItem = todoItemService.findOrFail(todoItemId);

        return todoItemConverter.toDto(todoItem);

    }

/*
    @GetMapping(path = "/get-all-by-todo-list/{todoListId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<TodoItem>> getAllByTodoListId(@PathVariable Long todoListId,
                                                                         @PageableDefault(page = 0, size = 10, sort = "id",
                                                                                 direction = Sort.Direction.ASC) Pageable pageable) {

        todoListService.findOrFail(todoListId);

//        Page<TodoItemBasicViewDto> todoItemBasicViewDto = todoItemConverter.todoItemBasicViewDtoPage(
//                todoItemRepo.findTodoItemsByTodoListId(todoListId));


        return ResponseEntity.status(HttpStatus.OK).body(
               todoItemService.findAll(todoListId, pageable));

    }
   */

    @GetMapping(path = "/get-all-by-todo-list/{todoListId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TodoItemBasicViewDto> getByTodoListId(@PathVariable Long todoListId) {

        todoListService.findOrFail(todoListId);

        List<TodoItem> todoItemsByTodoListId = todoItemService.findItemsByTodoList(todoListId);

        return todoItemConverter.todoItemBasicViewDtoList(todoItemsByTodoListId);

    }

    @GetMapping(path = "/get-all-by-todo-list/{todoListId}", produces = MediaType.APPLICATION_PDF_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<byte[]> getByTodoListIdOnPdf(@PathVariable Long todoListId){

        todoListService.findOrFail(todoListId);

        byte[] bytesPdf = toDoListPdfService.issueTodoList(todoListId);

        var headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=todo_list.pdf");

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .headers(headers)
                .body(bytesPdf);

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
