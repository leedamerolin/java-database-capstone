package com.project.back_end.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.project.back_end.service.TokenService;

import java.util.Map;

@Controller
public class DashboardController {

    @Autowired
    private TokenService tokenService;

    // ================= ADMIN DASHBOARD =================
    @GetMapping("/adminDashboard/{token}")
    public String adminDashboard(@PathVariable String token) {

        Map<String, Object> validation = tokenService.validateToken(token, "admin");

        if (validation.isEmpty()) {
            return "admin/adminDashboard"; // Thymeleaf template
        }

        return "redirect:/";
    }

    // ================= DOCTOR DASHBOARD =================
    @GetMapping("/doctorDashboard/{token}")
    public String doctorDashboard(@PathVariable String token) {

        Map<String, Object> validation = tokenService.validateToken(token, "doctor");

        if (validation.isEmpty()) {
            return "doctor/doctorDashboard"; // Thymeleaf template
        }

        return "redirect:/";
    }
}