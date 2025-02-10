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
        String sql = "SELECT flight_no, airline_code, departure_code, departure_name,  arrival_code, arrival_name, departure_date, departure_time, arrival_time, duration, price " +
                "FROM flight " +
                "WHERE departure_code = ? AND arrival_code = ? AND departure_date LIKE ?";

        // 날짜 형식을 "yyyy-mm-dd%"로 설정하여 정확히 날짜를 필터링
        String datePattern = date + "%";

        // 쿼리 실행 후 결과 반환
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
                        rs.getInt("price")
                ));
    }


}
