/*
 * Copyright (c) 2022.
 * @Author: Moises I da Silva
 * Email: dev.moises.dasilva@gmail.com
 */

package com.moises.todo.todorestapi.domain.exception;

public class EntityInUseException extends BusinessException{

    private static final long serialVersionUID = 1L;

    public EntityInUseException(String message) {
        super(message);
    }

}
