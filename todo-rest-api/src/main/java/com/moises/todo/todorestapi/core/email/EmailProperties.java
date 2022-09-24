/*
 * Copyright (c) 2022.
 * @Author: Moises I da Silva
 */

package com.moises.todo.todorestapi.core.email;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties("todo-app.email")
public class EmailProperties {

    private String sender;

}
