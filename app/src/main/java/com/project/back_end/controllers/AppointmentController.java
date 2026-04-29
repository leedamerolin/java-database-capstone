package com.project.back_end.controller;

import com.project.back_end.models.Appointment;
import com.project.back_end.service.AppointmentService;
import com.project.back_end.service.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private Service service;

    // ================= GET APPOINTMENTS =================
    @GetMapping("/{date}/{patientName}/{token}")
    public ResponseEntity<Map<String, Object>> getAppointments(
            @PathVariable String date,
            @PathVariable String patientName,
            @PathVariable String token) {

        // validate doctor token
        ResponseEntity<Map<String, String>> validation =
                service.validateToken(token, "doctor");

        if (!validation.getStatusCode().is2xxSuccessful()) {
            return ResponseEntity.status(401).body(Map.of("message", "Invalid token"));
        }

        LocalDate parsedDate = LocalDate.parse(date);

        return ResponseEntity.ok(
                appointmentService.getAppointment(patientName, parsedDate, token)
        );
    }

    // ================= BOOK APPOINTMENT =================
    @PostMapping("/{token}")
    public ResponseEntity<Map<String, String>> bookAppointment(
            @PathVariable String token,
            @RequestBody Appointment appointment) {

        // validate patient token
        ResponseEntity<Map<String, String>> validation =
                service.validateToken(token, "patient");

        if (!validation.getStatusCode().is2xxSuccessful()) {
            return ResponseEntity.status(401).body(Map.of("message", "Invalid token"));
        }

        int check = service.validateAppointment(appointment);

        if (check == -1) {
            return ResponseEntity.badRequest().body(Map.of("message", "Doctor not found"));
        }

        if (check == 0) {
            return ResponseEntity.badRequest().body(Map.of("message", "Slot not available"));
        }

        int result = appointmentService.bookAppointment(appointment);

        if (result == 1) {
            return ResponseEntity.status(201).body(Map.of("message", "Appointment booked"));
        }

        return ResponseEntity.badRequest().body(Map.of("message", "Error booking appointment"));
    }

    // ================= UPDATE APPOINTMENT =================
    @PutMapping("/{token}")
    public ResponseEntity<Map<String, String>> updateAppointment(
            @PathVariable String token,
            @RequestBody Appointment appointment) {

        ResponseEntity<Map<String, String>> validation =
                service.validateToken(token, "patient");

        if (!validation.getStatusCode().is2xxSuccessful()) {
            return ResponseEntity.status(401).body(Map.of("message", "Invalid token"));
        }

        return appointmentService.updateAppointment(appointment);
    }

    // ================= CANCEL APPOINTMENT =================
    @DeleteMapping("/{id}/{token}")
    public ResponseEntity<Map<String, String>> cancelAppointment(
            @PathVariable long id,
            @PathVariable String token) {

        ResponseEntity<Map<String, String>> validation =
                service.validateToken(token, "patient");

        if (!validation.getStatusCode().is2xxSuccessful()) {
            return ResponseEntity.status(401).body(Map.of("message", "Invalid token"));
        }

        return appointmentService.cancelAppointment(id, token);
    }
}