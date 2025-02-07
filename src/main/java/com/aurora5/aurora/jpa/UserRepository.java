package com.aurora5.aurora.jpa;

import com.aurora5.aurora.user.dto.UserDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {


    Optional<UserDto> findByUserId(String userid);
}
