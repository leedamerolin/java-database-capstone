package com.project.back_end.controller;

import com.project.back_end.models.Prescription;
import com.project.back_end.service.PrescriptionService;
import com.project.back_end.service.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("${api.path}/prescription")
public class PrescriptionController {

    @Autowired
    private PrescriptionService prescriptionService;

    @Autowired
    private Service service;

    // ================= SAVE PRESCRIPTION =================
    @PostMapping("/{token}")
    public ResponseEntity<Map<String, String>> savePrescription(
            @PathVariable String token,
            @RequestBody Prescription prescription) {

        // validate doctor token
        ResponseEntity<Map<String, String>> validation =
                service.validateToken(token, "doctor");

        if (!validation.getStatusCode().is2xxSuccessful()) {
            return ResponseEntity.status(401).body(Map.of("message", "Invalid token"));
        }

        return prescriptionService.savePrescription(prescription);
    }

    // ================= GET PRESCRIPTION =================
    @GetMapping("/{appointmentId}/{token}")
    public ResponseEntity<Map<String, Object>> getPrescription(
            @PathVariable Long appointmentId,
            @PathVariable String token) {

        // validate doctor token
        ResponseEntity<Map<String, String>> validation =
                service.validateToken(token, "doctor");

        if (!validation.getStatusCode().is2xxSuccessful()) {
            return ResponseEntity.status(401).body(Map.of("message", "Invalid token"));
        }

        return prescriptionService.getPrescription(appointmentId);
    }
}