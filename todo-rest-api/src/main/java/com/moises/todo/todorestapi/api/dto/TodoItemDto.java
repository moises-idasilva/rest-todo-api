package com.moises.todo.todorestapi.api.dto;

import com.moises.todo.todorestapi.api.dto.view.TodoListBasicViewDto;
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
    private TodoListBasicViewDto todoList;

}