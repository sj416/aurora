package com.aurora5.aurora.booking.dao;


import com.aurora5.aurora.user.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class BookingDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;
}
