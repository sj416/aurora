package com.aurora5.aurora.booking.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/booking")
public class BookingFlights {

    @RequestMapping("/flights")
    public String flights() {





        return "flights";
    }
}
