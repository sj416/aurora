package com.aurora5.aurora.flight.service;


import com.aurora5.aurora.flight.dto.FlightDto;
import org.springframework.stereotype.Service;

import java.util.List;


public interface FlightService {

    List<FlightDto> searchFlights(String departure, String arrival, String date);
}
