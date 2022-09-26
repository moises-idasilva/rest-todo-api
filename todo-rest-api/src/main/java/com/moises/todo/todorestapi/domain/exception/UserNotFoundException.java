/*
 * Copyright (c) 2022.
 * @Author: Moises I da Silva
 * Email: dev.moises.dasilva@gmail.com
 */

package com.moises.todo.todorestapi.domain.exception;

public class UserNotFoundException extends EntityNotFoundException{

    private static final long serialVersionUID = 1L;

    public UserNotFoundException(String userCode) {
        super(String.format("There is no record with User Code %s.", userCode));
    }


}
