package com.aurora5.aurora.booking.service;

import com.aurora5.aurora.booking.dao.BookingDao;
import com.aurora5.aurora.booking.dto.BookingDto;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {

    private BookingDao bookingDao;
    private final HttpSession session;

    @Autowired
    public BookingServiceImpl(BookingDao bookingDao, HttpSession session)
    {
        this.bookingDao = bookingDao;
        this.session = session;
    }

    @Transactional
    public void reserveFlight(int userNo, int flightNo) {
        bookingDao.reserveFlight(userNo, flightNo);
    }

    @Override
    public List<BookingDto> getBookingDetails(int userNo) {
        return bookingDao.findBookingDetailsByUserNo(userNo);
    }

    @Override
    public boolean cancelBooking(int bookingNo) {
        int result = bookingDao.cancelBooking(bookingNo);
        return result > 0; // 삭제 성공 여부 반환
    }
}

