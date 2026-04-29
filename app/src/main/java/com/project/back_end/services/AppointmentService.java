package com.project.back_end.service;

import com.project.back_end.models.Appointment;
import com.project.back_end.models.Patient;
import com.project.back_end.models.Doctor;
import com.project.back_end.repository.AppointmentRepository;
import com.project.back_end.repository.PatientRepository;
import com.project.back_end.repository.DoctorRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private TokenService tokenService;

    // ================= BOOK APPOINTMENT =================
    public int bookAppointment(Appointment appointment) {
        try {
            appointmentRepository.save(appointment);
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }

    // ================= UPDATE APPOINTMENT =================
    public ResponseEntity<Map<String, String>> updateAppointment(Appointment appointment) {

        Map<String, String> response = new HashMap<>();

        Optional<Appointment> existing = appointmentRepository.findById(appointment.getId());

        if (existing.isEmpty()) {
            response.put("message", "Appointment not found");
            return ResponseEntity.badRequest().body(response);
        }

        try {
            appointmentRepository.save(appointment);
            response.put("message", "Appointment updated successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("message", "Error updating appointment");
            return ResponseEntity.badRequest().body(response);
        }
    }

    // ================= CANCEL APPOINTMENT =================
    public ResponseEntity<Map<String, String>> cancelAppointment(long id, String token) {

        Map<String, String> response = new HashMap<>();

        Optional<Appointment> existing = appointmentRepository.findById(id);

        if (existing.isEmpty()) {
            response.put("message", "Appointment not found");
            return ResponseEntity.badRequest().body(response);
        }

        try {
            Appointment appointment = existing.get();
            appointmentRepository.delete(appointment);

            response.put("message", "Appointment cancelled successfully");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("message", "Error cancelling appointment");
            return ResponseEntity.badRequest().body(response);
        }
    }

    // ================= GET APPOINTMENTS =================
    public Map<String, Object> getAppointment(String pname, LocalDate date, String token) {

        Map<String, Object> response = new HashMap<>();

        // Extract doctorId from token
        Long doctorId = tokenService.getIdFromToken(token);

        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.plusDays(1).atStartOfDay();

        List<Appointment> appointments;

        if (pname == null || pname.equals("null")) {
            appointments = appointmentRepository
                    .findByDoctorIdAndAppointmentTimeBetween(doctorId, start, end);
        } else {
            appointments = appointmentRepository
                    .findByDoctorIdAndPatient_NameContainingIgnoreCaseAndAppointmentTimeBetween(
                            doctorId, pname, start, end);
        }

        response.put("appointments", appointments);

        return response;
    }
}