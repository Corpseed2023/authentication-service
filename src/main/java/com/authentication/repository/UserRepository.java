package com.authentication.repository;

import com.authentication.model.User;
import com.authentication.payload.request.UserRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findByMobileOrEmail(String mobile, String email);

    @Query("SELECT u FROM User u WHERE u.id = :userId")
    UserRequest findByUserId(Long userId);

}
