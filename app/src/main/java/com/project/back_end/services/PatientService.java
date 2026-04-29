package com.project.back_end.service;

import com.project.back_end.models.Patient;
import com.project.back_end.models.Appointment;
import com.project.back_end.dto.AppointmentDTO;
import com.project.back_end.repository.PatientRepository;
import com.project.back_end.repository.AppointmentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private TokenService tokenService;

    // ================= CREATE PATIENT =================
    public int createPatient(Patient patient) {
        try {
            patientRepository.save(patient);
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }

    // ================= GET PATIENT APPOINTMENTS =================
    public ResponseEntity<Map<String, Object>> getPatientAppointment(Long id, String token) {

        Map<String, Object> response = new HashMap<>();

        String email = tokenService.getEmailFromToken(token);
        Patient patient = patientRepository.findByEmail(email);

        if (patient == null || !patient.getId().equals(id)) {
            response.put("message", "Unauthorized");
            return ResponseEntity.status(401).body(response);
        }

        List<Appointment> appointments = appointmentRepository.findByPatientId(id);

        List<AppointmentDTO> dtoList = new ArrayList<>();

        for (Appointment a : appointments) {
            dtoList.add(new AppointmentDTO(
                    a.getId(),
                    a.getDoctor().getId(),
                    a.getDoctor().getName(),
                    a.getPatient().getId(),
                    a.getPatient().getName(),
                    a.getPatient().getEmail(),
                    a.getPatient().getPhone(),
                    a.getPatient().getAddress(),
                    a.getAppointmentTime(),
                    a.getStatus()
            ));
        }

        response.put("appointments", dtoList);
        return ResponseEntity.ok(response);
    }

    // ================= FILTER BY CONDITION =================
    public ResponseEntity<Map<String, Object>> filterByCondition(String condition, Long id) {

        Map<String, Object> response = new HashMap<>();

        int status = condition.equalsIgnoreCase("past") ? 1 : 0;

        List<Appointment> appointments =
                appointmentRepository.findByPatient_IdAndStatusOrderByAppointmentTimeAsc(id, status);

        response.put("appointments", appointments);
        return ResponseEntity.ok(response);
    }

    // ================= FILTER BY DOCTOR =================
    public ResponseEntity<Map<String, Object>> filterByDoctor(String name, Long patientId) {

        Map<String, Object> response = new HashMap<>();

        List<Appointment> appointments =
                appointmentRepository.filterByDoctorNameAndPatientId(name, patientId);

        response.put("appointments", appointments);
        return ResponseEntity.ok(response);
    }

    // ================= FILTER BY DOCTOR + CONDITION =================
    public ResponseEntity<Map<String, Object>> filterByDoctorAndCondition(String condition, String name, long patientId) {

        Map<String, Object> response = new HashMap<>();

        int status = condition.equalsIgnoreCase("past") ? 1 : 0;

        List<Appointment> appointments =
                appointmentRepository.filterByDoctorNameAndPatientIdAndStatus(name, patientId, status);

        response.put("appointments", appointments);
        return ResponseEntity.ok(response);
    }

    // ================= GET PATIENT DETAILS =================
    public ResponseEntity<Map<String, Object>> getPatientDetails(String token) {

        Map<String, Object> response = new HashMap<>();

        String email = tokenService.getEmailFromToken(token);

        Patient patient = patientRepository.findByEmail(email);

        if (patient == null) {
            response.put("message", "Patient not found");
            return ResponseEntity.badRequest().body(response);
        }

        response.put("patient", patient);
        return ResponseEntity.ok(response);
    }
} 