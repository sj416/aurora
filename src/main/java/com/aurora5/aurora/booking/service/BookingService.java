package com.aurora5.aurora.booking.service;

import com.aurora5.aurora.booking.dto.BookingDto;
import org.springframework.stereotype.Service;

import java.util.List;

public interface BookingService {

    public void reserveFlight(int userNo, int flightNo);

    List<BookingDto> getBookingDetails(int userNo);

    boolean cancelBooking(int bookingNo);
}
