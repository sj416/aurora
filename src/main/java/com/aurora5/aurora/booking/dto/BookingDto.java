package com.aurora5.aurora.booking.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class BookingDto {

    private int bookingNo;
    private LocalDateTime bookingDate;
    private String userName;
    private String email;
    private String gender;
    private String phone;
    private String departureName;
    private String departureCode;
    private String arrivalName;
    private String arrivalCode;
    private String departureDate;
    private String departureTime;
    private String arrivalTime;
    private String airlineCode;
    private int price;

    public BookingDto(int bookingNo, LocalDateTime bookingDate, String userName, String email, String gender, String phone,
                      String departureName, String departureCode, String arrivalName,
                      String arrivalCode, String departureDate, String departureTime,
                      String arrivalTime, String airlineCode, int price) {
        this.bookingNo = bookingNo;
        this.bookingDate = bookingDate;
        this.userName = userName;
        this.email = email;
        this.gender = gender;
        this.phone = phone;
        this.departureName = departureName;
        this.departureCode = departureCode;
        this.arrivalName = arrivalName;
        this.arrivalCode = arrivalCode;
        this.departureDate = departureDate;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.airlineCode = airlineCode;
        this.price = price;
    }



}
