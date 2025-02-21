package com.aurora5.aurora.user.service;


import com.aurora5.aurora.user.dao.UserDao;
import com.aurora5.aurora.user.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {
    private UserDao userDao;

    @Autowired  // ★ UserDao를 생성자 주입
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public int login(UserDto userDto) {
        return userDao.login(userDto.getUserId(), userDto.getUserPw());
    }

    @Override
    public Optional<UserDto> getUserInfo(String userId) {
        return userDao.getUserInfo(userId);
    }

    @Override
    public boolean createUser(UserDto userDto) {
        if (userDao.existsByUserId(userDto.getUserId())) {
            return false;
        }
        return userDao.insertUser(userDto);  // 사용자 정보를 데이터베이스에 저장
    }

    @Override
    public boolean isUserIdExist(String userId) {
        return userDao.existsByUserId(userId);
    }
}
