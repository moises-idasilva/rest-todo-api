package com.moises.todo.todorestapi.domain.service;

import com.moises.todo.todorestapi.domain.exception.EntityInUseException;
import com.moises.todo.todorestapi.domain.exception.TodoItemNotFoundException;
import com.moises.todo.todorestapi.domain.model.TodoItem;
import com.moises.todo.todorestapi.domain.repository.TodoItemRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TodoItemService {

    public static final String MSG_ENTITY_IN_USE = "ToDo Item with ID %d can't be deleted. " +
            "Reason: Other entity is using this entity.";

    @Autowired
    private TodoItemRepo todoItemRepo;

    @Transactional
    public TodoItem save(TodoItem todoItem) {

        return todoItemRepo.save(todoItem);

    }

    @Transactional
    public TodoItem update(TodoItem todoItem) {

        return save(todoItem);

    }

    public void delete(Long todoItemId) {

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


}
