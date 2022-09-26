/*
 * Copyright (c) 2022.
 * @Author: Moises I da Silva
 * Email: dev.moises.dasilva@gmail.com
 */

package com.moises.todo.todorestapi.domain.service;

public interface ToDoListPdfService {

    byte[] issueTodoList(Long todoListId);

}
