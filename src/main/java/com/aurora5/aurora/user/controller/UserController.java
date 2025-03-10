package com.aurora5.aurora.user.controller;

import com.aurora5.aurora.user.dto.UserDto;
import com.aurora5.aurora.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@Controller
@RequestMapping("/")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/")
    public String mainPage(){
        return "main";
    }

    @PostMapping("/loginProc")
    public ResponseEntity<Map<String, Object>> loginProc(@RequestBody UserDto userDto, HttpSession session) {
        Map<String, Object> response = new HashMap<>();

        // 1️⃣ userId로 사용자 정보 조회
        Optional<UserDto> loggedInUser = userService.getUserInfo(userDto.getUserId());

        if (loggedInUser.isEmpty()) {
            response.put("status", "error");
            response.put("message", "Invalid ID or password");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        // 2️⃣ 조회된 사용자 정보 가져오기
        UserDto user = loggedInUser.get();
        String storedHashedPassword = user.getUserPw();

        if (storedHashedPassword == null || storedHashedPassword.isEmpty()) {
            response.put("status", "error");
            response.put("message", "Password data error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

        // 3️⃣ 입력된 비밀번호와 저장된 해시 비밀번호 비교
        if (BCrypt.checkpw(userDto.getUserPw(), storedHashedPassword)) {
            // 4️⃣ 세션 저장
            session.setAttribute("loggedInUser", user);

            response.put("status", "success");
            response.put("message", "Login successful");
            return ResponseEntity.ok(response);
        } else {
            response.put("status", "error");
            response.put("message", "Invalid ID or password");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }


    @GetMapping("/user/join")
    public String joinPage() {
        return "join";
    }

    @PostMapping(value = "/user/insert", consumes = "application/json")
    public String insertUser(@RequestBody UserDto userDto) {

        String encryptedPassword = BCrypt.hashpw(userDto.getUserPw(), BCrypt.gensalt());

        userDto.setUserPw(encryptedPassword);

        userService.createUser(userDto);
        return "main";
    }

    @PostMapping("/user/idChk")
    public ResponseEntity<Integer> checkUserId(@RequestParam("userid") String userId) {
        boolean isExist = userService.isUserIdExist(userId);
        int result = isExist ? 1 : 0;
        return ResponseEntity.ok(result);  // 상태 코드 200 OK와 함께 반환
    }

    @GetMapping("/user/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "main";
    }
}
