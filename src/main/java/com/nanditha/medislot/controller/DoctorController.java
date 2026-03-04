package com.nanditha.medislot.controller;

import com.nanditha.medislot.model.Doctor;
import com.nanditha.medislot.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {
    @Autowired
    private DoctorRepository doctorRepository;

    @GetMapping
    public ResponseEntity<List<Doctor>> searchDoctors(
            @RequestParam(required = false) String specialization) {
        if (specialization != null) {
            return ResponseEntity.ok(
                doctorRepository.findBySpecializationContainingIgnoreCase(specialization));
        }
        return ResponseEntity.ok(doctorRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Doctor> getDoctor(@PathVariable Long id) {
        return doctorRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
