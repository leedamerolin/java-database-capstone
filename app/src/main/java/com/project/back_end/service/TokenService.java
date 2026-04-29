package com.project.back_end.service;

import com.project.back_end.repository.AdminRepository;
import com.project.back_end.repository.DoctorRepository;
import com.project.back_end.repository.PatientRepository;
import com.project.back_end.models.Admin;
import com.project.back_end.models.Doctor;
import com.project.back_end.models.Patient;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class TokenService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    // Secret from application.properties
    @Value("${jwt.secret}")
    private String secret;

    private static final long EXPIRATION = 1000 * 60 * 60 * 24 * 7; // 7 days

    // ================= GENERATE TOKEN =================
    public String generateToken(Long id, String userType) {

        String identifier = "";

        if ("admin".equals(userType)) {
            Admin admin = adminRepository.findById(id).orElse(null);
            if (admin != null) identifier = admin.getUsername();
        }

        if ("doctor".equals(userType)) {
            Doctor doctor = doctorRepository.findById(id).orElse(null);
            if (doctor != null) identifier = doctor.getEmail();
        }

        if ("patient".equals(userType)) {
            Patient patient = patientRepository.findById(id).orElse(null);
            if (patient != null) identifier = patient.getEmail();
        }

        return Jwts.builder()
                .setSubject(identifier)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // ================= EXTRACT IDENTIFIER =================
    public String extractIdentifier(String token) {
    return Jwts.parser()
            .setSigningKey(getSigningKey())
            .parseClaimsJws(token)
            .getBody()
            .getSubject();
}

    // ================= VALIDATE TOKEN =================
    public boolean validateToken(String token, String user) {

        try {
            String identifier = extractIdentifier(token);

            if ("admin".equals(user)) {
                Admin admin = adminRepository.findByUsername(identifier);
                return admin != null;
            }

            if ("doctor".equals(user)) {
                Doctor doctor = doctorRepository.findByEmail(identifier);
                return doctor != null;
            }

            if ("patient".equals(user)) {
                Patient patient = patientRepository.findByEmail(identifier);
                return patient != null;
            }

        } catch (Exception e) {
            return false;
        }

        return false;
    }

    // ================= GET SIGNING KEY =================
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    // ================= HELPER METHODS =================
    public String getEmailFromToken(String token) {
        return extractIdentifier(token);
    }

    public Long getIdFromToken(String token, String userType) {

        String identifier = extractIdentifier(token);

        if ("admin".equals(userType)) {
            Admin admin = adminRepository.findByUsername(identifier);
            return admin != null ? admin.getId() : null;
        }

        if ("doctor".equals(userType)) {
            Doctor doctor = doctorRepository.findByEmail(identifier);
            return doctor != null ? doctor.getId() : null;
        }

        if ("patient".equals(userType)) {
            Patient patient = patientRepository.findByEmail(identifier);
            return patient != null ? patient.getId() : null;
        }

        return null;
    }
} 