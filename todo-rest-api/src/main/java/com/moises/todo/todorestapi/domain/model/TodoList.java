/*
 * Copyright (c) 2022.
 * @Author: Moises I da Silva
 * Email: dev.moises.dasilva@gmail.com
 */

package com.moises.todo.todorestapi.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "todo_list")
public class TodoList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "completed")
    private Boolean completed = false;

    @Column(name = "color")
    private String color;

    @CreationTimestamp
    @Column(name = "created_on", nullable = false, columnDefinition = "datetime")
    private OffsetDateTime createdOn;

    @UpdateTimestamp
    @Column(name = "updated_on", nullable = false, columnDefinition = "datetime")
    private OffsetDateTime updatedOn;

    @ManyToOne
    private TodoDirectory todoDirectory;

    @OneToMany(mappedBy = "todoList")
    private List<TodoItem> todoItemList = new ArrayList<>();

    // Bellow are the methods to change the status of a list
    public void isCompleted() {
        this.completed = true;
    }

    public void isNotCompleted() {
        this.completed = false;
    }

}
