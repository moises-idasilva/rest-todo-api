package com.moises.todo.todorestapi.api.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public class UserDto {

    private Long id;
    private String userCode;
    private String activationCode;
    private boolean isActive;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String password;
    private TodoDirectoryDto todoDirectory;
    private OffsetDateTime createdOn;
    private OffsetDateTime updatedOn;

}
