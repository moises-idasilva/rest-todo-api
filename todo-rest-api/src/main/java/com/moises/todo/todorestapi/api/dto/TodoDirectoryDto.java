/*
 * Copyright (c) 2022.
 * @Author: Moises I da Silva
 * Email: dev.moises.dasilva@gmail.com
 */

package com.moises.todo.todorestapi.api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TodoDirectoryDto {

    private Long id;
    private String ownerCode;
    private String ownerUsername;
    private UserDto user;

}
