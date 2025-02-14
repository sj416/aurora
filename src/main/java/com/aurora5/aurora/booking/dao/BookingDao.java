package com.aurora5.aurora.booking.dao;


import com.aurora5.aurora.user.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class BookingDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    public void reserveFlight(int userNo, int flightNo) {
        String sql = "INSERT INTO booking (user_no, flight_no) VALUES (?, ?)";
        jdbcTemplate.update(sql, userNo, flightNo);
    }
}
