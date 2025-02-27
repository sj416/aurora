package com.aurora5.aurora.user.dao;

import com.aurora5.aurora.user.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public class UserDao {


    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int login(String userId, String userPw) {
        String sql = "SELECT COUNT(*) FROM user WHERE user_id = ? AND user_pw = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, userId, userPw);

        if (count == null) {
            System.out.println("쿼리 결과가 null입니다. 아이디와 비밀번호를 확인해주세요.");
            return 0;  // `null` 방지
        }

        return count;  // 반환된 카운트를 그대로 반환
    }

    // 로그인한 사용자 정보 조회
    public Optional<UserDto> getUserInfo(String userId) {
        String sql = "SELECT user_no, user_id, user_pw, user_name, email, phone, gender FROM user WHERE user_id = ?";

        try {
            UserDto user = jdbcTemplate.queryForObject(sql, new Object[]{userId}, (rs, rowNum) -> {


                return new UserDto(
                        rs.getInt("user_no"),
                        rs.getString("user_name"),
                        rs.getString("user_id"),
                        rs.getString("user_pw"),
                        rs.getString("email"),
                        rs.getString("gender"),
                        rs.getString("phone")
                );
            });
            return Optional.ofNullable(user);
        } catch (EmptyResultDataAccessException e) {
            // 쿼리 결과가 없으면 예외 처리
            return Optional.empty();
        }
    }

    public boolean existsByUserId(String userId) {
        String sql = "SELECT COUNT(*) FROM user WHERE user_id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, userId);
        return count != null && count > 0;  // 아이디가 존재하면 true 반환
    }

    public boolean insertUser(UserDto userDto) {
        String sql = "INSERT INTO user (user_id, user_pw, user_name, email, phone, gender) VALUES (?, ?, ?, ?, ?, ?)";
        int result = jdbcTemplate.update(sql, userDto.getUserId(), userDto.getUserPw(), userDto.getUsername(), userDto.getEmail(), userDto.getPhone(), userDto.getGender());
        return result > 0;
    }
}
