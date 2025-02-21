package com.aurora5.aurora.flight.service;

import com.aurora5.aurora.flight.dao.FlightDao;
import com.aurora5.aurora.flight.dto.FlightDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlightServiceImpl implements FlightService {

    private FlightDao flightDao;

    @Autowired  // ★ UserDao를 생성자 주입
    public FlightServiceImpl(FlightDao flightDao) {
        this.flightDao = flightDao;
    }

    @Override
    public List<FlightDto> searchFlights(String departureCode, String arrivalCode, String date) {
        return flightDao.findFlights(departureCode, arrivalCode, date);
    }
}

