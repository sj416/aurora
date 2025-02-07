package com.aurora5.aurora.user.service;

import com.aurora5.aurora.jpa.UserRepository;
import com.aurora5.aurora.user.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;


    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public int login(UserDto userDto) {
        Optional<UserDto> user = userRepository.findByUserId(userDto.getUserid());

        if (user.isPresent() && user.get().getUserpw().equals(userDto.getUserpw())) {
            return 1;  // 로그인 성공
        }
        return 0;
    }

    @Override
    public UserDto info(UserDto userDto) {
        Optional<UserDto> user = userRepository.findByUserId(userDto.getUserid());

        return user.map(value -> UserDto.builder()
                .userNo(value.getUserNo())
                .userid(value.getUserid())
                .username(value.getUsername())
                .email(value.getEmail())
                .gender(value.getGender())
                .build()).orElse(null);
    }
}
