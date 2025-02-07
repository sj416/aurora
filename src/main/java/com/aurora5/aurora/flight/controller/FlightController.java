package com.aurora5.aurora.flight.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/flights")
public class FlightController {

    @RequestMapping("/search")
    public String searchFlights() {


        return "flights";
    }

}
