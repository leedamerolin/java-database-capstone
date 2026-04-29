package com.project.back_end.service;

import com.project.back_end.models.Doctor;
import com.project.back_end.models.Appointment;
import com.project.back_end.dto.Login;
import com.project.back_end.repository.DoctorRepository;
import com.project.back_end.repository.AppointmentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private TokenService tokenService;

    // ================= GET DOCTOR AVAILABILITY =================
    public List<String> getDoctorAvailability(Long doctorId, LocalDate date) {

        Doctor doctor = doctorRepository.findById(doctorId).orElse(null);
        if (doctor == null) return new ArrayList<>();

        List<String> available = new ArrayList<>(doctor.getAvailableTimes());

        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.plusDays(1).atStartOfDay();

        List<Appointment> appointments =
                appointmentRepository.findByDoctorIdAndAppointmentTimeBetween(doctorId, start, end);

        for (Appointment ap : appointments) {
            String bookedTime = ap.getAppointmentTime().toLocalTime().toString();
            available.removeIf(slot -> slot.startsWith(bookedTime.substring(0, 5)));
        }

        return available;
    }

    // ================= SAVE DOCTOR =================
    public int saveDoctor(Doctor doctor) {
        try {
            Doctor existing = doctorRepository.findByEmail(doctor.getEmail());
            if (existing != null) return -1;

            doctorRepository.save(doctor);
            return 1;

        } catch (Exception e) {
            return 0;
        }
    }

    // ================= UPDATE DOCTOR =================
    public int updateDoctor(Doctor doctor) {
        try {
            if (!doctorRepository.existsById(doctor.getId())) {
                return -1;
            }

            doctorRepository.save(doctor);
            return 1;

        } catch (Exception e) {
            return 0;
        }
    }

    // ================= GET ALL DOCTORS =================
    public List<Doctor> getDoctors() {
        return doctorRepository.findAll();
    }

    // ================= DELETE DOCTOR =================
    public int deleteDoctor(long id) {
        try {
            if (!doctorRepository.existsById(id)) return -1;

            appointmentRepository.deleteAllByDoctorId(id);
            doctorRepository.deleteById(id);

            return 1;

        } catch (Exception e) {
            return 0;
        }
    }

    // ================= VALIDATE DOCTOR LOGIN =================
    public ResponseEntity<Map<String, String>> validateDoctor(Login login) {

        Map<String, String> response = new HashMap<>();

        Doctor doctor = doctorRepository.findByEmail(login.getIdentifier());

        if (doctor == null || !doctor.getPassword().equals(login.getPassword())) {
            response.put("message", "Invalid credentials");
            return ResponseEntity.badRequest().body(response);
        }

        String token = tokenService.generateToken(doctor.getId(), "doctor");

        response.put("token", token);
        return ResponseEntity.ok(response);
    }

    // ================= FIND BY NAME =================
    public Map<String, Object> findDoctorByName(String name) {

        Map<String, Object> res = new HashMap<>();

        List<Doctor> doctors = doctorRepository.findByNameLike(name);

        res.put("doctors", doctors);
        return res;
    }

    // ================= FILTER: NAME + SPECIALTY + TIME =================
    public Map<String, Object> filterDoctorsByNameSpecilityandTime(String name, String specialty, String amOrPm) {

        List<Doctor> doctors =
                doctorRepository.findByNameContainingIgnoreCaseAndSpecialtyIgnoreCase(name, specialty);

        doctors = filterDoctorByTime(doctors, amOrPm);

        Map<String, Object> res = new HashMap<>();
        res.put("doctors", doctors);
        return res;
    }

    // ================= FILTER: NAME + TIME =================
    public Map<String, Object> filterDoctorByNameAndTime(String name, String amOrPm) {

        List<Doctor> doctors = doctorRepository.findByNameLike(name);
        doctors = filterDoctorByTime(doctors, amOrPm);

        Map<String, Object> res = new HashMap<>();
        res.put("doctors", doctors);
        return res;
    }

    // ================= FILTER: NAME + SPECIALTY =================
    public Map<String, Object> filterDoctorByNameAndSpecility(String name, String specialty) {

        List<Doctor> doctors =
                doctorRepository.findByNameContainingIgnoreCaseAndSpecialtyIgnoreCase(name, specialty);

        Map<String, Object> res = new HashMap<>();
        res.put("doctors", doctors);
        return res;
    }

    // ================= FILTER: SPECIALTY + TIME =================
    public Map<String, Object> filterDoctorByTimeAndSpecility(String specialty, String amOrPm) {

        List<Doctor> doctors = doctorRepository.findBySpecialtyIgnoreCase(specialty);
        doctors = filterDoctorByTime(doctors, amOrPm);

        Map<String, Object> res = new HashMap<>();
        res.put("doctors", doctors);
        return res;
    }

    // ================= FILTER: SPECIALTY =================
    public Map<String, Object> filterDoctorBySpecility(String specialty) {

        List<Doctor> doctors = doctorRepository.findBySpecialtyIgnoreCase(specialty);

        Map<String, Object> res = new HashMap<>();
        res.put("doctors", doctors);
        return res;
    }

    // ================= FILTER: TIME =================
    public Map<String, Object> filterDoctorsByTime(String amOrPm) {

        List<Doctor> doctors = doctorRepository.findAll();
        doctors = filterDoctorByTime(doctors, amOrPm);

        Map<String, Object> res = new HashMap<>();
        res.put("doctors", doctors);
        return res;
    }

    // ================= PRIVATE FILTER METHOD =================
    private List<Doctor> filterDoctorByTime(List<Doctor> doctors, String amOrPm) {

        List<Doctor> filtered = new ArrayList<>();

        for (Doctor doctor : doctors) {

            for (String slot : doctor.getAvailableTimes()) {

                int hour = Integer.parseInt(slot.substring(0, 2));

                if ("AM".equalsIgnoreCase(amOrPm) && hour < 12) {
                    filtered.add(doctor);
                    break;
                }

                if ("PM".equalsIgnoreCase(amOrPm) && hour >= 12) {
                    filtered.add(doctor);
                    break;
                }
            }
        }

        return filtered;
    }
} 