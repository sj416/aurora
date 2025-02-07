//package com.aurora5.aurora.user.dao;
//
//import com.aurora5.aurora.user.dto.UserDto;
//import com.aurora5.aurora.jpa.UserEntity;
//import com.aurora5.aurora.jpa.UserRepository;
//import org.springframework.stereotype.Repository;
//
//@Repository
//public class UserDao {
//
//    private final UserRepository userRepository;
//
//    public UserDao(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
//    public int login(UserDto userDto) {
//        UserEntity user = userRepository.findByUserIdAndUserPw(userDto.getUserId(), userDto.getUserPw());
//        return (user != null) ? 1 : 0;
//    }
//
//    public UserDto info(UserDto userDto) {
//        return  userRepository.findByUsername(userDto.getUserId()).orElse(null);
//
//    }
//
//}
