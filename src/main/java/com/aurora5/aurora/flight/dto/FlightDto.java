package com.aurora5.aurora.flight.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class FlightDto {

    private int flightId;
    private String departure;
    private String arrival;
    private Date departureDate;
    private String departureTime;
    private String arrivalTime;
    private String duration;
    private String airlineCode;
    private int price;


    public FlightDto(int flightId, String departure,String arrival , Date departureDate, String departureTime, String arrivalTime, String duration, String airlineCode, int price) {
        this.flightId = flightId;
        this.departure = departure;
        this.arrival = arrival;
        this.departureDate = departureDate;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.duration = duration;
        this.airlineCode = airlineCode;
        this.price = price;
    }
}
