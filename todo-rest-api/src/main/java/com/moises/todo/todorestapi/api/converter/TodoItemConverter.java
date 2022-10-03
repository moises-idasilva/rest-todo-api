/*
 * Copyright (c) 2022.
 * @Author: Moises I da Silva
 * Email: dev.moises.dasilva@gmail.com
 */

package com.moises.todo.todorestapi.api.converter;

import com.moises.todo.todorestapi.api.dto.TodoItemDto;
import com.moises.todo.todorestapi.api.dto.input.TodoItemInputDto;
import com.moises.todo.todorestapi.api.dto.view.TodoItemBasicViewDto;
import com.moises.todo.todorestapi.domain.model.TodoItem;
import com.moises.todo.todorestapi.domain.repository.TodoItemRepo;
import com.moises.todo.todorestapi.domain.service.TodoItemService;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TodoItemConverter {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TodoItemService todoItemService;

    @Autowired
    private TodoItemRepo todoItemRepo;

    public TodoItem toEntity(TodoItemInputDto todoItemInputDto) {

        return modelMapper.map(todoItemInputDto, TodoItem.class);

    }

    public TodoItemDto toDto(TodoItem todoItem) {

        return modelMapper.map(todoItem, TodoItemDto.class);

    }

    public TodoItemBasicViewDto toTodoItemBasicViewDto(TodoItem todoItem) {

        return modelMapper.map(todoItem, TodoItemBasicViewDto.class);

    }

    public void copyToEntity(TodoItemInputDto todoItemInputDto, TodoItem todoItem) {

        todoItemInputDto.setCompleted(todoItem.getCompleted());

        modelMapper.map(todoItemInputDto, todoItem);

    }

    public List<TodoItemBasicViewDto> todoItemBasicViewDtoList(List<TodoItem> todoItemList) {

        return todoItemList.stream()
                .map(todoItem -> toTodoItemBasicViewDto(todoItem))
                .collect(Collectors.toList());

    }

//    public Page<TodoItemBasicViewDto> todoItemBasicViewDtoPage(Long todoItemList, Pageable pageable) {
//
//        Page<TodoItem> todoItemPage = (Page<TodoItem>) todoItemRepo.findTodoItemsByTodoListId(todoItemList);
//        Page<TodoItemBasicViewDto> todoItemBasicViewDtoPage = todoItemPage.map(new Converter<TodoItem, TodoItemBasicViewDto>() {
//            @Override
//            public TodoItemBasicViewDto convert(MappingContext<TodoItem, TodoItemBasicViewDto> mappingContext) {
//
//                return modelMapper.map(TodoItem.class, TodoItemBasicViewDto.class);
//            }
//
//
//                return vert();
//
//    }


}
