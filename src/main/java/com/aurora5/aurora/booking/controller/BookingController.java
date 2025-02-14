package com.aurora5.aurora.booking.controller;


import com.aurora5.aurora.booking.dto.BookingDto;
import com.aurora5.aurora.booking.service.BookingService;
import jakarta.servlet.http.HttpSession;
import org.apache.catalina.User;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/reserve")
    public ResponseEntity<String> reserveFlight(@RequestBody BookingDto bookingDto, HttpSession session) {
        int userNo = (Integer) session.getAttribute("user_no");
        bookingService.reserveFlight(userNo, bookingDto.getFlightNo());
        return ResponseEntity.ok("항공편 예약이 완료되었습니다.");
    }



}
