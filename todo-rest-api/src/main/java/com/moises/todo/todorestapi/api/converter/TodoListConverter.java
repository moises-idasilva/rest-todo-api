package com.moises.todo.todorestapi.api.converter;

import com.moises.todo.todorestapi.api.dto.TodoListDto;
import com.moises.todo.todorestapi.api.dto.input.TodoListInputDto;
import com.moises.todo.todorestapi.api.dto.view.TodoListBasicViewDto;
import com.moises.todo.todorestapi.domain.model.TodoList;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TodoListConverter {

    @Autowired
    private ModelMapper modelMapper;

    public TodoList toEntity(TodoListInputDto todoListInputDto) {

        return modelMapper.map(todoListInputDto, TodoList.class);

    }

    public TodoListDto toDto(TodoList todoList) {

        return modelMapper.map(todoList, TodoListDto.class);

    }

    public TodoListBasicViewDto toTodoListBasicInfoDto(TodoList todoList) {

        return modelMapper.map(todoList, TodoListBasicViewDto.class);

    }

    public void copyToEntity(TodoListInputDto todoListInputDto, TodoList todoList) {

            modelMapper.map(todoListInputDto, todoList);

    }

    public List<TodoListBasicViewDto>  toCollectionWithTodoListBasicInfoDtoList(List<TodoList> todoLists) {

        return todoLists.stream()
                .map(todoList -> toTodoListBasicInfoDto(todoList))
                .collect(Collectors.toList());

    }

}
