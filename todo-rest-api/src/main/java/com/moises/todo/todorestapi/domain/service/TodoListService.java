package com.moises.todo.todorestapi.domain.service;

import com.moises.todo.todorestapi.domain.exception.EntityInUseException;
import com.moises.todo.todorestapi.domain.exception.TodoListNotFoundException;
import com.moises.todo.todorestapi.domain.model.TodoDirectory;
import com.moises.todo.todorestapi.domain.model.TodoList;
import com.moises.todo.todorestapi.domain.repository.TodoListRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TodoListService {

    public static final String MSG_ENTITY_IN_USE = "ToDo List with ID %d can't be deleted. " +
            "Reason: Other entity is using this entity.";

    @Autowired
    private TodoListRepo todoListRepo;

    @Autowired
    private TodoDirectoryService todoDirectoryService;

    @Transactional
    public TodoList save(String todoDirectoryOwnerCode, TodoList todoList) {

        //String todoDirectoryOwnerCode = todoList.getTodoDirectory().getOwnerCode();
        TodoDirectory todoDirectory = todoDirectoryService.findOrFail(todoDirectoryOwnerCode);

        todoList.setTodoDirectory(todoDirectory);

        return todoListRepo.save(todoList);

    }

    @Transactional
    public TodoList update(TodoList todoList) {

        return todoListRepo.save(todoList);

    }

    public void delete(Long todoListId) {

        try {
            todoListRepo.deleteTodoListById(todoListId);
            todoListRepo.flush();

        } catch (EmptyResultDataAccessException e) {
            throw new TodoListNotFoundException(todoListId);

        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(
                    String.format(MSG_ENTITY_IN_USE, todoListId));
        }

    }

    public TodoList findOrFail(Long todoListId) {

        return todoListRepo.findTodoListById(todoListId).orElseThrow(() -> new TodoListNotFoundException(todoListId));

    }


}
