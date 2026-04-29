package com.project.back_end.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.back_end.models.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

    // Find admin by username
    Admin findByUsername(String username);
}