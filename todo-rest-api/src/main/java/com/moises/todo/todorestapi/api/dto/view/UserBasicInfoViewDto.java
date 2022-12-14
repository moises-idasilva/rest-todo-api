/*
 * Copyright (c) 2022.
 * @Author: Moises I da Silva
 * Email: dev.moises.dasilva@gmail.com
 */

package com.moises.todo.todorestapi.api.dto.view;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserBasicInfoViewDto {

    private String firstName;
    private String lastName;
    private String userCode;
    private String username;
    private String email;

}
