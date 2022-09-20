package com.moises.todo.todorestapi.api.converter;

import com.moises.todo.todorestapi.api.dto.TodoDirectoryDto;
import com.moises.todo.todorestapi.domain.model.TodoDirectory;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TodoConverter {

    @Autowired
    private ModelMapper modelMapper;

    public void copyToEntity(TodoDirectoryDto todoDirectoryInputDto, TodoDirectory todoDirectory) {

        modelMapper.map(todoDirectoryInputDto, todoDirectory);

    }

    public TodoDirectoryDto toDto(TodoDirectory todoDirectory) {

        return modelMapper.map(todoDirectory, TodoDirectoryDto.class);

    }

}
