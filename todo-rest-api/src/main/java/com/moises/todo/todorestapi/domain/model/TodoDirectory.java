/*
 * Copyright (c) 2022.
 * @Author: Moises I da Silva
 * Email: dev.moises.dasilva@gmail.com
 */

package com.moises.todo.todorestapi.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "todo_directory")
public class TodoDirectory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name = "id")
    private Long id;

    @Column(name = "owner_code", unique = true)
    private String ownerCode;

    @Column(name = "owner_username", unique = true)
    private String ownerUsername;

    @OneToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "FK_USER_ID_TODO_DIRECTORY"))
    private User user;

    @OneToMany(mappedBy = "todoDirectory")
    private List<TodoList> todoLists = new ArrayList<>();

}
