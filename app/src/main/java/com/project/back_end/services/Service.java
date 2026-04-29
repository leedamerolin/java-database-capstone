package com.project.back_end.service;

import com.project.back_end.models.*;
import com.project.back_end.dto.Login;
import com.project.back_end.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class Service {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private PatientService patientService;

    // ================= VALIDATE TOKEN =================
    public ResponseEntity<Map<String, String>> validateToken(String token, String user) {

        Map<String, String> response = new HashMap<>();

        boolean valid = tokenService.validateToken(token, user);

        if (!valid) {
            response.put("message", "Invalid or expired token");
            return ResponseEntity.status(401).body(response);
        }

        return ResponseEntity.ok(response);
    }

    // ================= VALIDATE ADMIN =================
    public ResponseEntity<Map<String, String>> validateAdmin(Admin receivedAdmin) {

        Map<String, String> response = new HashMap<>();

        Admin admin = adminRepository.findByUsername(receivedAdmin.getUsername());

        if (admin == null || !admin.getPassword().equals(receivedAdmin.getPassword())) {
            response.put("message", "Invalid credentials");
            return ResponseEntity.badRequest().body(response);
        }

        String token = tokenService.generateToken(admin.getId(), "admin");

        response.put("token", token);
        return ResponseEntity.ok(response);
    }

    // ================= FILTER DOCTOR =================
    public Map<String, Object> filterDoctor(String name, String specialty, String time) {

        if (!"null".equals(name) && !"null".equals(specialty) && !"null".equals(time)) {
            return doctorService.filterDoctorsByNameSpecilityandTime(name, specialty, time);
        }

        if (!"null".equals(name) && !"null".equals(specialty)) {
            return doctorService.filterDoctorByNameAndSpecility(name, specialty);
        }

        if (!"null".equals(name) && !"null".equals(time)) {
            return doctorService.filterDoctorByNameAndTime(name, time);
        }

        if (!"null".equals(specialty) && !"null".equals(time)) {
            return doctorService.filterDoctorByTimeAndSpecility(specialty, time);
        }

        if (!"null".equals(name)) {
            return doctorService.findDoctorByName(name);
        }

        if (!"null".equals(specialty)) {
            return doctorService.filterDoctorBySpecility(specialty);
        }

        if (!"null".equals(time)) {
            return doctorService.filterDoctorsByTime(time);
        }

        Map<String, Object> res = new HashMap<>();
        res.put("doctors", doctorService.getDoctors());
        return res;
    }

    // ================= VALIDATE APPOINTMENT =================
    public int validateAppointment(Appointment appointment) {

        Doctor doctor = doctorRepository.findById(appointment.getDoctor().getId()).orElse(null);

        if (doctor == null) return -1;

        List<String> available =
                doctorService.getDoctorAvailability(
                        doctor.getId(),
                        appointment.getAppointmentTime().toLocalDate()
                );

        String time = appointment.getAppointmentTime().toLocalTime().toString().substring(0, 5);

        for (String slot : available) {
            if (slot.startsWith(time)) {
                return 1;
            }
        }

        return 0;
    }

    // ================= VALIDATE PATIENT =================
    public boolean validatePatient(Patient patient) {

        Patient existing =
                patientRepository.findByEmailOrPhone(patient.getEmail(), patient.getPhone());

        return existing == null;
    }

    // ================= VALIDATE PATIENT LOGIN =================
    public ResponseEntity<Map<String, String>> validatePatientLogin(Login login) {

        Map<String, String> response = new HashMap<>();

        Patient patient = patientRepository.findByEmail(login.getIdentifier());

        if (patient == null || !patient.getPassword().equals(login.getPassword())) {
            response.put("message", "Invalid credentials");
            return ResponseEntity.badRequest().body(response);
        }

        String token = tokenService.generateToken(patient.getId(), "patient");

        response.put("token", token);
        return ResponseEntity.ok(response);
    }

    // ================= FILTER PATIENT =================
    public ResponseEntity<Map<String, Object>> filterPatient(String condition, String name, String token) {

        String email = tokenService.getEmailFromToken(token);

        Patient patient = patientRepository.findByEmail(email);

        if (patient == null) {
            Map<String, Object> res = new HashMap<>();
            res.put("message", "Unauthorized");
            return ResponseEntity.status(401).body(res);
        }

        if (!"null".equals(condition) && !"null".equals(name)) {
            return patientService.filterByDoctorAndCondition(condition, name, patient.getId());
        }

        if (!"null".equals(condition)) {
            return patientService.filterByCondition(condition, patient.getId());
        }

        if (!"null".equals(name)) {
            return patientService.filterByDoctor(name, patient.getId());
        }

        return patientService.getPatientAppointment(patient.getId(), token);
    }
} 