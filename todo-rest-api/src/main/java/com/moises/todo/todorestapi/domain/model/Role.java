/*
 * Copyright (c) 2022.
 * @Author: Moises I da Silva
 * Email: dev.moises.dasilva@gmail.com
 */

package com.moises.todo.todorestapi.domain.model;

import com.moises.todo.todorestapi.domain.enums.RoleName;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Getter
@Setter
public class Role implements GrantedAuthority, Serializable {

    private static final long serialVersionUID = -1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "role_name", nullable = false)
    private RoleName roleName;

//    @ManyToMany(mappedBy = "roles")
//    private Collection<User> users;


    @Override
    public String getAuthority() {
        return this.roleName.toString();
    }

}
