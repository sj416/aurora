package com.aurora5.aurora.booking.controller;

import com.aurora5.aurora.booking.dto.BookingDto;
import com.aurora5.aurora.booking.service.BookingService;
import com.aurora5.aurora.booking.service.TossPaymentService;
import com.aurora5.aurora.user.dto.UserDto;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/booking")
public class BookingController {

    private final BookingService bookingService;
    private final TossPaymentService tossPaymentService;


    public BookingController(BookingService bookingService, TossPaymentService tossPaymentService) {
        this.bookingService = bookingService;
        this.tossPaymentService = tossPaymentService;
    }

    @PostMapping("/reserve")
    public ResponseEntity<String> reserveFlight(@RequestBody Map<String, Object> request, HttpSession session) {
        Integer flightNo = (Integer) request.get("flightNo");
        Integer amount = (Integer) request.get("amount");
        String paymentKey = (String) request.get("paymentKey");
        String orderId = (String) request.get("orderId");

        System.out.println("Received paymentKey: " + paymentKey);
        System.out.println("Received orderId: " + orderId);
        System.out.println("Received amount: " + amount);

        if (flightNo == null || amount == null || paymentKey == null || orderId == null) {
            return ResponseEntity.badRequest().body("필수 파라미터(flightNo, amount, paymentKey, orderId)가 필요합니다.");
        }

        UserDto loggedInUser = (UserDto) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }

        int userNo = loggedInUser.getUserNo();

        // ✅ 항공편 예약 처리
        bookingService.reserveFlight(userNo, flightNo);

        return ResponseEntity.ok("항공편 예약이 완료되었습니다.");
    }


    @PostMapping("/payment/verify")
    public ResponseEntity<Map<String, Object>> verifyPayment(@RequestBody Map<String, Object> request) {
        String paymentKey = (String) request.get("paymentKey");
        String orderId = (String) request.get("orderId");
        int amount = (Integer) request.get("amount");

        // 결제 검증 수행
        boolean isPaymentValid = tossPaymentService.verifyPayment(paymentKey, orderId, amount);

        // 응답 데이터 생성
        Map<String, Object> response = new HashMap<>();
        response.put("success", isPaymentValid);

        if (isPaymentValid) {
            response.put("message", "결제 검증 성공");
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "결제 검증 실패");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }


    @GetMapping("/reserve/details")
    public String getBookingDetails(HttpSession session, Model model) {
        UserDto loggedInUser = (UserDto) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/"; // 로그인 안 되어 있으면 로그인 페이지로 리디렉션
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
