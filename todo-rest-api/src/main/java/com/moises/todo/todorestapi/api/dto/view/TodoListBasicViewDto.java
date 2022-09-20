package com.moises.todo.todorestapi.api.dto.view;

import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public class TodoListBasicViewDto {

    private Long id;
    private String name;
    private String description;
    private Boolean completed;
    private String color;

    private OffsetDateTime createdOn;
    private OffsetDateTime updatedOn;


}