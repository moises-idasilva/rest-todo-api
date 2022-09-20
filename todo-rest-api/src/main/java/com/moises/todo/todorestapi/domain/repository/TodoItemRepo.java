package com.moises.todo.todorestapi.domain.repository;

import com.moises.todo.todorestapi.domain.model.TodoItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TodoItemRepo extends JpaRepository<TodoItem, Long> {

    Optional<TodoItem> findTodoItemById(Long id);

    void deleteTodoItemById(Long id);

}
