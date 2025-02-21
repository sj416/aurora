package com.aurora5.aurora.booking.controller;

import com.aurora5.aurora.booking.dto.BookingDto;
import com.aurora5.aurora.booking.service.BookingService;
import com.aurora5.aurora.user.dto.UserDto;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/booking")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/reserve")
    public ResponseEntity<String> reserveFlight(@RequestBody Map<String, Integer> request, HttpSession session) {
        Integer flightNo = request.get("flightNo");
        if (flightNo == null) {
            return ResponseEntity.badRequest().body("flightNo 파라미터가 필요합니다.");
        }

        UserDto loggedInUser = (UserDto) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }

        int userNo = loggedInUser.getUserNo();
        bookingService.reserveFlight(userNo, flightNo);

        return ResponseEntity.ok("항공편 예약이 완료되었습니다.");
    }

    @GetMapping("/reserve/details")
    public String getBookingDetails(HttpSession session, Model model) {
        UserDto loggedInUser = (UserDto) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/login"; // 로그인 안 되어 있으면 로그인 페이지로 리디렉션
        }

        List<BookingDto> bookings = bookingService.getBookingDetails(loggedInUser.getUserNo());
        model.addAttribute("bookings", bookings);
        return "reserve_list"; // 예약 내역을 보여줄 뷰 페이지 이름
    }

    @PostMapping("/cancel")
    public ResponseEntity<String> cancelBooking(@RequestParam("bookingNo") int bookingNo) {
        boolean isCancelled = bookingService.cancelBooking(bookingNo);

        if (isCancelled) {
            return ResponseEntity.ok("예약이 취소되었습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("예약 취소에 실패했습니다.");
        }
    }
}
