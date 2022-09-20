package com.moises.todo.todorestapi.api.dto.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
public class UserWithPasswordInputDto extends UserInputDto{

    @NotBlank
    private String password;

}
