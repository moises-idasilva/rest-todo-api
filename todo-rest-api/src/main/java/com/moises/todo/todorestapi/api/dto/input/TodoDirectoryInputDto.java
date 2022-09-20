package com.moises.todo.todorestapi.api.dto.input;

import com.moises.todo.todorestapi.api.dto.UserDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TodoDirectoryInputDto {

    private String ownerCode;
    private String ownerUsername;

}
