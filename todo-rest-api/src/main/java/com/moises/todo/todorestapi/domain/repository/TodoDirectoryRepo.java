/*
 * Copyright (c) 2022.
 * @Author: Moises I da Silva
 * Email: dev.moises.dasilva@gmail.com
 */

package com.moises.todo.todorestapi.domain.repository;

import com.moises.todo.todorestapi.domain.model.TodoDirectory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TodoDirectoryRepo extends JpaRepository<TodoDirectory, Long> {

    Optional<TodoDirectory> findByOwnerCode(String ownerCode);

}
