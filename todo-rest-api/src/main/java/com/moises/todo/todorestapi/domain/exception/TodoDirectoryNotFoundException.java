package com.moises.todo.todorestapi.domain.exception;

public class TodoDirectoryNotFoundException extends EntityNotFoundException{

    private static final long serialVersionUID = 1L;

    public TodoDirectoryNotFoundException(String ownerCode) {
        super(String.format("There is no record for ToDo Directory with Owner Code %s.", ownerCode));
    }


}
