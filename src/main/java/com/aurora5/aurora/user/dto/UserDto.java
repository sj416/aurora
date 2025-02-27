package com.aurora5.aurora.user.dto;


import lombok.*;

import java.io.Serial;
import java.io.Serializable;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private int userNo;

    private String username, userId, userPw, email, gender, phone ;



    @Override
    public String toString() {
        return "UserDto{" +
                "userNo=" + userNo +
                ", username='" + username + '\'' +
                ", userId='" + userId + '\'' +
                ", userPw='" + userPw + '\'' +
                ", email='" + email + '\'' +
                ", gender='" + gender + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
