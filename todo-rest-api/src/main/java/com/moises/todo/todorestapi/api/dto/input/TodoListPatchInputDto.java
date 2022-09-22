package com.moises.todo.todorestapi.api.dto.input;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TodoListPatchInputDto {

    private String name;
    private String description;
    private Boolean completed;
    private String color;

}