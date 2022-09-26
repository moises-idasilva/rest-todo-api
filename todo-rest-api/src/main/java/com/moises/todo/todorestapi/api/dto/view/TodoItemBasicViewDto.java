/*
 * Copyright (c) 2022.
 * @Author: Moises I da Silva
 * Email: dev.moises.dasilva@gmail.com
 */

package com.moises.todo.todorestapi.api.dto.view;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TodoItemBasicViewDto {

    private Long id;
    private String name;
    private String listName;
    private String description;
    private Boolean completed;
    private String color;

}