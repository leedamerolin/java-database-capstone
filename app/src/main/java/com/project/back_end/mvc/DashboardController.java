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

    boolean valid = tokenService.validateToken(token, "admin");

    if (valid) {
        return "admin/adminDashboard";
    }

    return "redirect:/";
}

    // ================= DOCTOR DASHBOARD =================
    @GetMapping("/doctorDashboard/{token}")
public String doctorDashboard(@PathVariable String token) {

    boolean valid = tokenService.validateToken(token, "doctor");

    if (valid) {
        return "doctor/doctorDashboard";
    }

    return "redirect:/";
}
}