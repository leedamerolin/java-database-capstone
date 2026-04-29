package com.project.back_end.repository;

import com.project.back_end.models.Doctor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    // ================= FIND BY EMAIL =================
    Doctor findByEmail(String email);


    // ================= FIND BY NAME LIKE =================
    @Query("SELECT d FROM Doctor d WHERE LOWER(d.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Doctor> findByNameLike(@Param("name") String name);


    // ================= FIND BY NAME + SPECIALTY =================
    @Query("SELECT d FROM Doctor d " +
           "WHERE LOWER(d.name) LIKE LOWER(CONCAT('%', :name, '%')) " +
           "AND LOWER(d.specialty) = LOWER(:specialty)")
    List<Doctor> findByNameContainingIgnoreCaseAndSpecialtyIgnoreCase(
            @Param("name") String name,
            @Param("specialty") String specialty
    );


    // ================= FIND BY SPECIALTY =================
    List<Doctor> findBySpecialtyIgnoreCase(String specialty);
}