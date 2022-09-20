package com.moises.todo.todorestapi.api.dto.view;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TodoDirectoryBasicInfoDto {

    private String ownerCode;
    private String ownerUsername;

    private List<TodoListBasicViewDto> todoList;

}
