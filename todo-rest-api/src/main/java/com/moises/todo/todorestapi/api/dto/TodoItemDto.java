/*
 * Copyright (c) 2022.
 * @Author: Moises I da Silva
 * Email: dev.moises.dasilva@gmail.com
 */

package com.moises.todo.todorestapi.api.dto;

import com.moises.todo.todorestapi.api.dto.view.TodoListBasicViewWithNoItemsDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TodoItemDto {

    private Long id;
    private String name;
    private String description;
    private Boolean completed;
    private String color;
    private TodoListBasicViewWithNoItemsDto todoList;

}