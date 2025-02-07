package com.aurora5.aurora.user.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;




@Getter
@Setter
@Builder
public class UserDto {


    private int userNo;

    private String username, userid, userpw, email, gender, phone ;

    public UserDto(int userNo, String username, String userid, String userpw, String email, String gender, String phone) {
        this.userNo = userNo;
        this.username = username;
        this.userid = userid;
        this.userpw = userpw;
        this.email = email;
        this.gender = gender;
        this.phone = phone;
    }

}
