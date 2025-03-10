package com.aurora5.aurora.flight.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FlightDto {

    private int flightNo;
    private String departureCode;
    private String departureName;
    private String arrivalCode;
    private String arrivalName;
    private String departureDate;
    private String departureTime;
    private String arrivalTime;
    private String duration;
    private String airlineCode;
    private int price;
    private int seats;

}
