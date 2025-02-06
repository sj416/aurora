package com.aurora5.aurora.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class UserController {

    @GetMapping("/main")
    public String mainPage(){


        return "main";
    }

    @RequestMapping("/user/join")
    public String joinPage(){


        return "join";
    }
}
