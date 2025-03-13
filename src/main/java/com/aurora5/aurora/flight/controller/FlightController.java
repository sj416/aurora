package com.aurora5.aurora.flight.controller;

import com.aurora5.aurora.flight.dto.FlightDto;
import com.aurora5.aurora.flight.service.FlightService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/flights")
public class FlightController {

    private FlightService flightService;

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @GetMapping("/search")
    public String search(){
        return "search";
    }

    @GetMapping("/list")
    public ResponseEntity<List<FlightDto>> searchFlights(
            @RequestParam String departure,
            @RequestParam String arrival,
            @RequestParam String date) {

        List<FlightDto> flights = flightService.searchFlights(departure, arrival, date);

        if (flights.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(flights);
    }

}

