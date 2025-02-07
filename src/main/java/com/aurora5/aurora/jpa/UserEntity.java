package com.aurora5.aurora.jpa;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="user")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_no")
    private int userNo;

    @Column(nullable = false, name = "usre_name")
    private String username;

    @Column(nullable = false, unique = true, name = "user_id")
    private String userId;

    @Column(nullable = false, name = "user_pw")
    private String userPw;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String phone;
}
