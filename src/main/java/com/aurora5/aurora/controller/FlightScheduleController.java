package com.aurora5.aurora.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/flights")
public class FlightScheduleController {

    @RequestMapping("/search")
    public String searchFlights() {


        return "flights";
    }

}
