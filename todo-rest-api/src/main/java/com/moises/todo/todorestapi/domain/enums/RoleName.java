package com.moises.todo.todorestapi.domain.enums;

public enum RoleName {

    ROLE_ADMIN, // Can do everything, including add new users
    ROLE_USER,  // Same as ADMIN less add new users
    ROLE_GUEST; // Only Ready capabilities

}
