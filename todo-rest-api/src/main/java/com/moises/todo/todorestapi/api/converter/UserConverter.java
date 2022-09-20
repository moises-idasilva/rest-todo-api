package com.moises.todo.todorestapi.api.converter;

import com.moises.todo.todorestapi.api.dto.UserDto;
import com.moises.todo.todorestapi.api.dto.input.UserInputDto;
import com.moises.todo.todorestapi.api.dto.view.UserBasicInfoViewDto;
import com.moises.todo.todorestapi.domain.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserConverter{

    @Autowired
    private ModelMapper modelMapper;

    public User toEntity(UserInputDto userInputDto) {
        return modelMapper.map(userInputDto, User.class);
    }

    public void copyToEntity(UserInputDto userInputDto, User user) {
        modelMapper.map(userInputDto, user);
    }

    public UserDto toDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }

    public UserBasicInfoViewDto toUserBasicInfoDto(User user) {
        return modelMapper.map(user, UserBasicInfoViewDto.class);
    }

    public List<UserDto> toCollectionDTO(List<User> userList) {
        return userList.stream()
                .map(user -> toDto(user))
                .collect(Collectors.toList());
    }

    public List<UserBasicInfoViewDto> toCollectionWithBasicUserInfoDTO(List<User> userList) {
        return userList.stream()
                .map(user -> toUserBasicInfoDto(user))
                .collect(Collectors.toList());
    }


}
