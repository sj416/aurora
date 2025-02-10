package com.aurora5.aurora.user.dto;


import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {


    private int userNo;

    private String username, userId, userPw, email, gender, phone ;

    public UserDto(int userNo, String userId, String username, String email, String phone, String gender) {
    }
}
