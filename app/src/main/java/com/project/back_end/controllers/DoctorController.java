package com.project.back_end.controller;

import com.project.back_end.models.Doctor;
import com.project.back_end.dto.Login;
import com.project.back_end.service.DoctorService;
import com.project.back_end.service.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("${api.path}/doctor")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private Service service;

    // ================= GET DOCTOR AVAILABILITY =================
    @GetMapping("/availability/{user}/{doctorId}/{date}/{token}")
    public ResponseEntity<Map<String, Object>> getAvailability(
            @PathVariable String user,
            @PathVariable Long doctorId,
            @PathVariable String date,
            @PathVariable String token) {

        ResponseEntity<Map<String, String>> validation =
                service.validateToken(token, user);

        if (!validation.getStatusCode().is2xxSuccessful()) {
            return ResponseEntity.status(401).body(Map.of("message", "Invalid token"));
        }

        LocalDate parsedDate = LocalDate.parse(date);

        return ResponseEntity.ok(
                Map.of("availability",
                        doctorService.getDoctorAvailability(doctorId, parsedDate))
        );
    }

    // ================= GET ALL DOCTORS =================
    @GetMapping
    public Map<String, Object> getDoctors() {
        return Map.of("doctors", doctorService.getDoctors());
    }

    // ================= ADD DOCTOR =================
    @PostMapping("/{token}")
    public ResponseEntity<Map<String, String>> addDoctor(
            @PathVariable String token,
            @RequestBody Doctor doctor) {

        ResponseEntity<Map<String, String>> validation =
                service.validateToken(token, "admin");

        if (!validation.getStatusCode().is2xxSuccessful()) {
            return ResponseEntity.status(401).body(Map.of("message", "Invalid token"));
        }

        int result = doctorService.saveDoctor(doctor);

        if (result == 1) {
            return ResponseEntity.ok(Map.of("message", "Doctor added to db"));
        }

        if (result == 0) {
            return ResponseEntity.status(409).body(Map.of("message", "Doctor already exists"));
        }

        return ResponseEntity.status(500).body(Map.of("message", "Some internal error occurred"));
    }

    // ================= DOCTOR LOGIN =================
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> doctorLogin(@RequestBody Login login) {
        return doctorService.validateDoctor(login);
    }

    // ================= UPDATE DOCTOR =================
    @PutMapping("/{token}")
    public ResponseEntity<Map<String, String>> updateDoctor(
            @PathVariable String token,
            @RequestBody Doctor doctor) {

        ResponseEntity<Map<String, String>> validation =
                service.validateToken(token, "admin");

        if (!validation.getStatusCode().is2xxSuccessful()) {
            return ResponseEntity.status(401).body(Map.of("message", "Invalid token"));
        }

        int result = doctorService.updateDoctor(doctor);

        if (result == 1) {
            return ResponseEntity.ok(Map.of("message", "Doctor updated"));
        }

        if (result == -1) {
            return ResponseEntity.status(404).body(Map.of("message", "Doctor not found"));
        }

        return ResponseEntity.status(500).body(Map.of("message", "Some internal error occurred"));
    }

    // ================= DELETE DOCTOR =================
    @DeleteMapping("/{id}/{token}")
    public ResponseEntity<Map<String, String>> deleteDoctor(
            @PathVariable Long id,
            @PathVariable String token) {

        ResponseEntity<Map<String, String>> validation =
                service.validateToken(token, "admin");

        if (!validation.getStatusCode().is2xxSuccessful()) {
            return ResponseEntity.status(401).body(Map.of("message", "Invalid token"));
        }

        int result = doctorService.deleteDoctor(id);

        if (result == 1) {
            return ResponseEntity.ok(Map.of("message", "Doctor deleted successfully"));
        }

        if (result == -1) {
            return ResponseEntity.status(404).body(Map.of("message", "Doctor not found with id"));
        }

        return ResponseEntity.status(500).body(Map.of("message", "Some internal error occurred"));
    }

    // ================= FILTER DOCTORS =================
    @GetMapping("/filter/{name}/{time}/{speciality}")
    public Map<String, Object> filterDoctors(
            @PathVariable String name,
            @PathVariable String time,
            @PathVariable String speciality) {

        return service.filterDoctor(name, speciality, time);
    }
}