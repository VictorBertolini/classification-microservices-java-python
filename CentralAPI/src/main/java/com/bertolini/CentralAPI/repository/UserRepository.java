package com.bertolini.CentralAPI.repository;

import com.bertolini.CentralAPI.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserId(Long userId);
    User findUserByEmail(String email);

    @Modifying
    @Transactional
    @Query("UPDATE User u " +
            "SET u.requestsRemain = " +
            "CASE u.role " +
            "   WHEN 'FREE'    THEN 100" +
            "   WHEN 'PREMIUM' THEN 1000" +
            "   WHEN 'ADMIN'   THEN 10000" +
            "END"
    )
    public int resetAllUserRequests();
}
