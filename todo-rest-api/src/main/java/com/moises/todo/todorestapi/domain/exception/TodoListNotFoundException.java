/*
 * Copyright (c) 2022.
 * @Author: Moises I da Silva
 * Email: dev.moises.dasilva@gmail.com
 */

package com.moises.todo.todorestapi.domain.exception;

public class TodoListNotFoundException extends EntityNotFoundException{

    private static final long serialVersionUID = 1L;

    public TodoListNotFoundException(Long id) {
        super(String.format("There is no record with ToDo List id %d.", id));
    }


}
