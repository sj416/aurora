package com.aurora5.aurora.booking.controller;


import com.aurora5.aurora.booking.service.BookingService;
import jakarta.servlet.http.HttpSession;
import org.apache.catalina.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;



@Controller
@RequestMapping("/booking")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping("/reserve")
    public String showReservationPage(HttpSession session, Model model) {
        // 세션에서 사용자 정보 가져오기
        User user = (User) session.getAttribute("user"); // 세션에 저장된 사용자 정보
        if (user != null) {
            model.addAttribute("user", user); // 사용자 정보를 모델에 추가
        } else {
            // 로그인하지 않은 경우 처리 (로그인 페이지로 리디렉션 등)
            return "redirect:/login";
        }

        // 예약 페이지로 이동
        return "reserve";
    }



}
