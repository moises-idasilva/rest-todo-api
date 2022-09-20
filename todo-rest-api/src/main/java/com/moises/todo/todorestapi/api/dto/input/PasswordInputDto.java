package com.moises.todo.todorestapi.api.dto.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class PasswordInputDto {

    @NotBlank
    private String currentPassword;

    @NotBlank
    private String newPassword;

}
