/*
 * Copyright (c) 2022.
 * @Author: Moises I da Silva
 * Email: dev.moises.dasilva@gmail.com
 */

package com.moises.todo.todorestapi.api.dto.view;

import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
public class TodoListBasicViewDto {

    private Long id;
    private String name;
    private String description;
    private Boolean completed;
    private String color;

    private List<TodoItemBasicViewDto> todoItemList;

    private OffsetDateTime createdOn;
    private OffsetDateTime updatedOn;


}