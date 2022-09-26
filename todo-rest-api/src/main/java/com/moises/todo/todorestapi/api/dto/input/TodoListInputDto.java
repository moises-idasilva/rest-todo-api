/*
 * Copyright (c) 2022.
 * @Author: Moises I da Silva
 * Email: dev.moises.dasilva@gmail.com
 */

package com.moises.todo.todorestapi.api.dto.input;

import com.moises.todo.todorestapi.api.dto.TodoItemDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TodoListInputDto {

    private String name;
    private String description;
//    private Boolean completed;
    private String color;
    private List<TodoItemDto> todoItemDtoList;

}