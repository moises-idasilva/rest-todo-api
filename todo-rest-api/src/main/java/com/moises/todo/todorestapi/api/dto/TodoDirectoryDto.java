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
