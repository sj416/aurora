package com.aurora5.aurora.booking.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/booking")
public class BookingFlights {

    @RequestMapping("/flights")
    public String flights(Model model) {

        return "flights";
    }
}
