package com.project.back_end.controller;

import com.project.back_end.models.Patient;
import com.project.back_end.dto.Login;
import com.project.back_end.service.PatientService;
import com.project.back_end.service.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/patient")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @Autowired
    private Service service;

    // ================= GET PATIENT DETAILS =================
    @GetMapping("/{token}")
    public ResponseEntity<Map<String, Object>> getPatientDetails(
            @PathVariable String token) {

        ResponseEntity<Map<String, String>> validation =
                service.validateToken(token, "patient");

        if (!validation.getStatusCode().is2xxSuccessful()) {
            return ResponseEntity.status(401).body(Map.of("message", "Invalid token"));
        }

        return patientService.getPatientDetails(token);
    }

    // ================= CREATE PATIENT =================
    @PostMapping
    public ResponseEntity<Map<String, String>> createPatient(
            @RequestBody Patient patient) {

        boolean valid = service.validatePatient(patient);

        if (!valid) {
            return ResponseEntity.status(409)
                    .body(Map.of("message", "Patient with email id or phone no already exist"));
        }

        int result = patientService.createPatient(patient);

        if (result == 1) {
            return ResponseEntity.ok(Map.of("message", "Signup successful"));
        }

        return ResponseEntity.status(500)
                .body(Map.of("message", "Internal server error"));
    }

    // ================= PATIENT LOGIN =================
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> patientLogin(
            @RequestBody Login login) {

        return service.validatePatientLogin(login);
    }

    // ================= GET PATIENT APPOINTMENTS =================
    @GetMapping("/{id}/{token}")
    public ResponseEntity<Map<String, Object>> getAppointments(
            @PathVariable Long id,
            @PathVariable String token) {

        ResponseEntity<Map<String, String>> validation =
                service.validateToken(token, "patient");

        if (!validation.getStatusCode().is2xxSuccessful()) {
            return ResponseEntity.status(401).body(Map.of("message", "Invalid token"));
        }

        return patientService.getPatientAppointment(id, token);
    }

    // ================= FILTER PATIENT APPOINTMENTS =================
    @GetMapping("/filter/{condition}/{name}/{token}")
    public ResponseEntity<Map<String, Object>> filterAppointments(
            @PathVariable String condition,
            @PathVariable String name,
            @PathVariable String token) {

        ResponseEntity<Map<String, String>> validation =
                service.validateToken(token, "patient");

        if (!validation.getStatusCode().is2xxSuccessful()) {
            return ResponseEntity.status(401).body(Map.of("message", "Invalid token"));
        }

        return service.filterPatient(condition, name, token);
    }
}