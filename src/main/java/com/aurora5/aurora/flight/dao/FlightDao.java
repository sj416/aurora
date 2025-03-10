package com.aurora5.aurora.flight.dao;

import com.aurora5.aurora.flight.dto.FlightDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FlightDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    public List<FlightDto> findFlights(String departureCode, String arrivalCode, String date) {
        String sql = "SELECT flight_no, airline_code, departure_code, departure_name,  arrival_code, arrival_name, departure_date, departure_time, arrival_time, duration, price,seats " +
                "FROM forpriceEn " +
                "WHERE departure_code = ? AND arrival_code = ? AND departure_date LIKE ?" +
                "ORDER BY price ASC";

        String datePattern = date + "%";

        return jdbcTemplate.query(sql, new Object[]{departureCode, arrivalCode, datePattern},
                (rs, rowNum) -> new FlightDto(
                        rs.getInt("flight_no"),
                        rs.getString("departure_code"),
                        rs.getString("departure_name"),
                        rs.getString("arrival_code"),
                        rs.getString("arrival_name"),
                        rs.getString("departure_date"),
                        rs.getString("departure_time"),
                        rs.getString("arrival_time"),
                        rs.getString("duration"),
                        rs.getString("airline_code"),
                        rs.getInt("price"),
                        rs.getInt("seats")
                ));
    }

}
