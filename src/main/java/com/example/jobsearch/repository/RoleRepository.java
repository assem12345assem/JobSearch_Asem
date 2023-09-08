package com.example.jobsearch.repository;

import com.example.jobsearch.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByRole(String guest);
}
