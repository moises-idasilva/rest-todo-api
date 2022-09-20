package com.moises.todo.todorestapi.domain.repository;

import com.moises.todo.todorestapi.domain.model.TodoList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TodoListRepo extends JpaRepository<TodoList, Long> {

    Optional<TodoList> findTodoListById(Long id);

    void deleteTodoListById(Long id);

}
