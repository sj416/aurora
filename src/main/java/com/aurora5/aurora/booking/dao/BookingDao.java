package com.aurora5.aurora.booking.dao;


import com.aurora5.aurora.booking.dto.BookingDto;
import com.aurora5.aurora.user.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Repository
public class BookingDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    public void reserveFlight(int userNo, int flightNo) {
        String sql = "INSERT INTO booking (user_no, flight_no, booking_date) VALUES (?, ?, ?)";
        LocalDateTime nowKST = LocalDateTime.now(ZoneId.of("Asia/Seoul")); // UTC+9 변환
        jdbcTemplate.update(sql, userNo, flightNo, nowKST);

        // forpriceEn 테이블에서 해당 항공편의 좌석 수 감소
        String updateSql = "UPDATE forpriceEn SET seats = seats - 1 WHERE flight_no = ? AND seats > 0";
        int updatedRows = jdbcTemplate.update(updateSql, flightNo);

        // 좌석이 부족한 경우 예외 처리
        if (updatedRows == 0) {
            throw new IllegalStateException("There are no seats available to reserve");
        }
    }


    public List<BookingDto> findBookingDetailsByUserNo(int userNo) {
        String sql = "SELECT " +
                "b.booking_no, b.booking_date, u.user_name, u.email, u.gender, u.phone, " +
                "f.departure_name, f.departure_code, f.arrival_name, f.arrival_code, " +
                "f.departure_date, f.departure_time, f.arrival_time, " +
                "f.airline_code, f.price " +
                "FROM booking b " +
                "JOIN user u ON b.user_no = u.user_no " +
                "JOIN forpriceEn f ON b.flight_no = f.flight_no " +
                "WHERE b.user_no = ?";

        return jdbcTemplate.query(sql, new Object[]{userNo}, (rs, rowNum) -> new BookingDto(
                rs.getInt("booking_no"),
                rs.getTimestamp("booking_date").toLocalDateTime(),  // booking_date 추가
                rs.getString("user_name"),
                rs.getString("email"),
                rs.getString("gender"),
                rs.getString("phone"),
                rs.getString("departure_name"),
                rs.getString("departure_code"),
                rs.getString("arrival_name"),
                rs.getString("arrival_code"),
                rs.getString("departure_date"),
                rs.getString("departure_time"),
                rs.getString("arrival_time"),
                rs.getString("airline_code"),
                rs.getInt("price")
        ));
    }

    public int cancelBooking(int bookingNo) {
        // 예약된 항공편 번호 조회
        String selectSql = "SELECT flight_no FROM booking WHERE booking_no = ?";
        Integer flightNo = jdbcTemplate.queryForObject(selectSql, Integer.class, bookingNo);

        if (flightNo == null) {
            throw new IllegalArgumentException("This reservation does not exist");
        }

        // 예약 취소 (삭제)
        String deleteSql = "DELETE FROM booking WHERE booking_no = ?";
        int deletedRows = jdbcTemplate.update(deleteSql, bookingNo);

        // 좌석 복구 (삭제가 성공했을 경우)
        if (deletedRows > 0) {
            String updateSql = "UPDATE forpriceEn SET seats = seats + 1 WHERE flight_no = ?";
            jdbcTemplate.update(updateSql, flightNo);
        }

        return deletedRows;
    }

}
