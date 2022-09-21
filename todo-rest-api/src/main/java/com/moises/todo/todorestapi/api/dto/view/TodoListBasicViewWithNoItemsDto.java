package com.moises.todo.todorestapi.api.dto.view;

import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
public class TodoListBasicViewWithNoItemsDto {

    private Long id;
    private String name;
    private String description;

}