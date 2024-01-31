package com.authentication.repository;

import com.authentication.model.User;
import com.authentication.payload.request.UserRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigInteger;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findByMobileOrEmail(String mobile, String email);

    @Query("SELECT u FROM User u WHERE u.id = :userId")
    User findByUserId(Long userId);



    @Query(value = "SELECT COUNT(*) > 0 FROM user WHERE (email = :email OR mobile = :mobile) AND company_id = :companyId", nativeQuery= true)
    BigInteger existsByEmailOrMobileAndCompanyId(@Param("email") String email, @Param("mobile") String mobile,
                                                 @Param("companyId") Long companyId);



}
