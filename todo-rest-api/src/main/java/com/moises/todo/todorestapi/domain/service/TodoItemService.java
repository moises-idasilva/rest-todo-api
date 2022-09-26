/*
 * Copyright (c) 2022.
 * @Author: Moises I da Silva
 * Email: dev.moises.dasilva@gmail.com
 */

package com.moises.todo.todorestapi.domain.service;

import com.moises.todo.todorestapi.domain.exception.EntityInUseException;
import com.moises.todo.todorestapi.domain.exception.TodoItemNotFoundException;
import com.moises.todo.todorestapi.domain.model.TodoItem;
import com.moises.todo.todorestapi.domain.model.TodoList;
import com.moises.todo.todorestapi.domain.repository.TodoItemRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TodoItemService {

    public static final String MSG_ENTITY_IN_USE = "ToDo Item with ID %d can't be deleted. " +
            "Reason: Other entity is using this entity.";

    @Autowired
    private TodoItemRepo todoItemRepo;

    @Autowired
    private TodoListService todoListService;

    @Transactional
    public TodoItem save(Long todoListId, TodoItem todoItem) {

        TodoList todoList = todoListService.findOrFail(todoListId);

        todoItem.setTodoList(todoList);
        todoItem.setCompleted(false);
        todoItem.setListName(todoList.getName());

        return todoItemRepo.save(todoItem);

    }

    @Transactional
    public TodoItem update(TodoItem todoItem) {

        return todoItemRepo.save(todoItem);

    }

    @Transactional
    public void delete(Long todoItemId) {

        findOrFail(todoItemId);

        try {
            todoItemRepo.deleteTodoItemById(todoItemId);
            todoItemRepo.flush();

        } catch (EmptyResultDataAccessException e) {
            throw new TodoItemNotFoundException(todoItemId);

        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(
                    String.format(MSG_ENTITY_IN_USE, todoItemId));
        }

    }

    public TodoItem findOrFail(Long todoItemId) {

        return todoItemRepo.findTodoItemById(todoItemId).orElseThrow(
                () -> new TodoItemNotFoundException(todoItemId)
        );

    }

    public List<TodoItem> findItemsByTodoList(Long todoListId) {

        todoListService.findOrFail(todoListId);

        return todoItemRepo.findTodoItemsByTodoListId(todoListId);

    }

    public void setAsCompleted(Long todoItemId) {

        TodoItem todoItemPresent = findOrFail(todoItemId);

        todoItemPresent.isCompleted();

        update(todoItemPresent);

    }

    public void setAsNotCompleted(Long todoItemId) {

        TodoItem todoItemPresent = findOrFail(todoItemId);

        todoItemPresent.isNotCompleted();

        update(todoItemPresent);

    }


}
