package com.nanditha.medislot.controller;

import com.nanditha.medislot.model.Doctor;
import com.nanditha.medislot.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/doctors")
@CrossOrigin(origins = "*")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @PostMapping("/register")
    public ResponseEntity<?> registerDoctor(
            Authentication authentication,
            @RequestBody Map<String, Object> request
    ) {
        try {
            String username = authentication.getName();
            Doctor doctor = doctorService.registerDoctor(
                    username,
                    (String) request.get("specialization"),
                    (String) request.get("phone"),
                    (String) request.get("hospital"),
                    (Integer) request.get("experienceYears"),
                    ((Number) request.get("consultationFee")).doubleValue()
            );
            return ResponseEntity.ok(doctor);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Doctor>> getAllDoctors(
            @RequestParam(required = false) String specialization
    ) {
        if (specialization != null) {
            return ResponseEntity.ok(
                    doctorService.getDoctorsBySpecialization(specialization)
            );
        }
        return ResponseEntity.ok(doctorService.getAllDoctors());
    }

    @GetMapping("/available")
    public ResponseEntity<List<Doctor>> getAvailableDoctors() {
        return ResponseEntity.ok(doctorService.getAvailableDoctors());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDoctorById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(doctorService.getDoctorById(id));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getDoctorProfile(Authentication authentication) {
        try {
            return ResponseEntity.ok(
                    doctorService.getDoctorByUsername(authentication.getName())
            );
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/availability")
    public ResponseEntity<?> updateAvailability(
            Authentication authentication,
            @RequestBody Map<String, Boolean> request
    ) {
        try {
            Doctor doctor = doctorService.updateAvailability(
                    authentication.getName(),
                    request.get("available")
            );
            return ResponseEntity.ok(doctor);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}