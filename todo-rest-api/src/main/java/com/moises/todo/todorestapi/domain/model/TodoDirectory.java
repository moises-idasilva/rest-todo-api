package com.moises.todo.todorestapi.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "todo_directory")
public class TodoDirectory {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "owner_code", unique = true)
    private String ownerCode;

    @Column(name = "owner_username", unique = true)
    private String ownerUsername;

    @OneToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "FK_USER_ID_TODO_DIRECTORY"))
    private User user;

}
