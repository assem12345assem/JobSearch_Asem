package com.example.jobsearch.repository;

import com.example.jobsearch.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> getByEmail(String email);

    @Modifying
    @Query(value = "INSERT INTO users_roles (user_email, role_id) VALUES (:email, :roleId)", nativeQuery = true)
    void assignRoleToUser(@Param("email") String email, @Param("roleId") Long roleId);

    Optional<User> findByResetPasswordToken(String token);

}
