package com.aurora5.aurora.user.controller;

import com.aurora5.aurora.user.dto.UserDto;
import com.aurora5.aurora.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.logging.Logger;


@Controller
@RequestMapping("/")
public class UserController {
    private final UserService userService;

    // 생성자 주입 방식 (권장)
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/main")
    public String mainPage(){


        return "main";
    }

    @PostMapping("/user/loginProc")
    public String loginProc(@RequestBody UserDto userDto, HttpSession session) {
        int isLogin = userService.login(userDto);
        System.out.println("로그인 결과: " + isLogin);

        if (isLogin > 0) {  // 로그인 성공
            UserDto loggedInUser = userService.info(userDto);
            session.setAttribute("loggedInUser", loggedInUser);
            return "redirect:/flights/search";
        } else {  // 로그인 실패
            session.invalidate();
            return "redirect:/main";
        }
    }



    @RequestMapping("/user/join")
    public String joinPage(){



        return "join";
    }
}
