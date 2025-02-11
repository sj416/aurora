package com.aurora5.aurora.booking.service;

import com.aurora5.aurora.booking.dao.BookingDao;
import com.aurora5.aurora.booking.dto.BookingDto;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookingServiceImpl implements BookingService {

    private BookingDao bookingDao;

    @Autowired
    public BookingServiceImpl(BookingDao bookingDao) {
        this.bookingDao = bookingDao;
    }


}

