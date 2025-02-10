package com.aurora5.aurora.user.service;

import com.aurora5.aurora.user.dto.UserDto;

import java.util.Optional;


public interface UserService {


    int login(UserDto userDto);

    Optional<UserDto> getUserInfo(String userId);

    boolean createUser(UserDto userDto);

    boolean isUserIdExist(String userId);
}
