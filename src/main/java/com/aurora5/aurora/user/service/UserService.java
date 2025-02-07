package com.aurora5.aurora.user.service;

import com.aurora5.aurora.user.dto.UserDto;



public interface UserService {


    int login(UserDto userDto);

    UserDto info(UserDto userDto);
}
