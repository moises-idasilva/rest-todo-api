/*
 * Copyright (c) 2022.
 * @Author: Moises I da Silva
 * Email: dev.moises.dasilva@gmail.com
 */

package com.moises.todo.todorestapi.api.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
public class TodoListDto {

    private Long id;
    private String name;
    private String description;
    private Boolean completed;
    private String color;

    private TodoDirectoryDto todoDirectory;
    private List<TodoItemDto> todoItemList;

    private OffsetDateTime createdOn;
    private OffsetDateTime updatedOn;

}