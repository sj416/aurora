package com.aurora5.aurora.user.controller;

import com.aurora5.aurora.user.dto.UserDto;
import com.aurora5.aurora.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

    // 생성자 주입 방식 (권장)
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @RequestMapping("/main")
    public String mainPage(){
        return "main";
    }

    @PostMapping("/loginProc")
    public ResponseEntity<Map<String, Object>> loginProc(@RequestBody UserDto userDto, HttpSession session) {
        int isLogin = userService.login(userDto);
        System.out.println("로그인 결과: " + isLogin);

        Map<String, Object> response = new HashMap<>();

        if (isLogin != 0) {
            // 로그인 성공
            Optional<UserDto> loggedInUser = userService.getUserInfo(userDto.getUserId());
            if (loggedInUser.isPresent()) {
                session.setAttribute("loggedInUser", loggedInUser.get());
                response.put("status", "success");
                response.put("message", "로그인 성공");
                return ResponseEntity.ok(response);  // 로그인 성공 시 JSON 응답 반환
            } else {
                response.put("status", "error");
                response.put("message", "사용자 정보를 찾을 수 없습니다.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);  // 사용자 정보 없음
            }
        } else {
            // 로그인 실패
            response.put("status", "error");
            response.put("message", "아이디 또는 비밀번호가 잘못되었습니다.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);  // 로그인 실패
        }
    }

    @GetMapping("/user/join")
    public String joinPage() {
        return "join";
    }

    @PostMapping(value = "/user/insert", consumes = "application/json")
    public String insertUser(@RequestBody UserDto userDto) {
        userService.createUser(userDto);
        return "main";
    }

    @PostMapping("/user/idChk")
    public ResponseEntity<Integer> checkUserId(@RequestParam("userid") String userId) {
        boolean isExist = userService.isUserIdExist(userId);
        int result = isExist ? 1 : 0;
        return ResponseEntity.ok(result);  // 상태 코드 200 OK와 함께 반환

    }
}
