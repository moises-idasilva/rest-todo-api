/*
 * Copyright (c) 2022.
 * @Author: Moises I da Silva
 * Email: dev.moises.dasilva@gmail.com
 */

package com.moises.todo.todorestapi.api.controller;

import com.moises.todo.todorestapi.api.converter.TodoDirectoryConverter;
import com.moises.todo.todorestapi.api.dto.view.TodoDirectoryBasicInfoDto;
import com.moises.todo.todorestapi.domain.service.TodoDirectoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/todo-directories")
public class TodoDirectoryController {

    @Autowired
    private TodoDirectoryService todoDirectoryService;

    @Autowired
    private TodoDirectoryConverter todoDirectoryConverter;

    @GetMapping("/{ownerCode}")
    public TodoDirectoryBasicInfoDto getByOwnerCode(@PathVariable String ownerCode) {
        return  todoDirectoryConverter.toBasicInfoDto(todoDirectoryService.findOrFail(ownerCode));
    }

}
