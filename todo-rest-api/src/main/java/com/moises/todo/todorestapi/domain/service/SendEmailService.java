/*
 * Copyright (c) 2022.
 * @Author: Moises I da Silva
 * Email: dev.moises.dasilva@gmail.com
 */

package com.moises.todo.todorestapi.domain.service;


import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;

import java.util.Map;
import java.util.Set;

public interface SendEmailService {

    void send(Message message);

    @Getter
    @Builder
    class Message {

        @Singular
        private Set<String> recipients;

        @NonNull
        private String subject;

        @NonNull
        private String body;

        @Singular("variable")
        private Map<String, Object> variables;
        
    }

}
