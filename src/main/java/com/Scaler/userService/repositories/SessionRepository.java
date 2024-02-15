package com.Scaler.userService.repositories;

import com.Scaler.userService.models.Session;
import com.Scaler.userService.models.SessionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<Session,Long> {
    public Optional<Session> findByTokenAndUser_Id(String token,Long userId);
}
