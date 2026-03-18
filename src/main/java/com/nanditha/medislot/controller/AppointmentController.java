package com.nanditha.medislot.controller;

import com.nanditha.medislot.dto.AppointmentRequest;
import com.nanditha.medislot.model.Appointment;
import com.nanditha.medislot.service.AppointmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/appointments")
@CrossOrigin(origins = "*")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @PostMapping("/book")
    public ResponseEntity<?> bookAppointment(
            Authentication authentication,
            @Valid @RequestBody AppointmentRequest request
    ) {
        try {
            Appointment appointment = appointmentService.bookAppointment(
                    authentication.getName(), request
            );
            return ResponseEntity.ok(appointment);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/my")
    public ResponseEntity<?> getMyAppointments(Authentication authentication) {
        try {
            List<Appointment> appointments = appointmentService
                    .getPatientAppointments(authentication.getName());
            return ResponseEntity.ok(appointments);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/doctor")
    public ResponseEntity<?> getDoctorAppointments(Authentication authentication) {
        try {
            List<Appointment> appointments = appointmentService
                    .getDoctorAppointments(authentication.getName());
            return ResponseEntity.ok(appointments);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{appointmentId}/status")
    public ResponseEntity<?> updateStatus(
            @PathVariable Long appointmentId,
            Authentication authentication,
            @RequestBody Map<String, String> request
    ) {
        try {
            Appointment.Status status = Appointment.Status.valueOf(request.get("status"));
            Appointment appointment = appointmentService.updateAppointmentStatus(
                    appointmentId, status, authentication.getName()
            );
            return ResponseEntity.ok(appointment);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{appointmentId}")
    public ResponseEntity<?> cancelAppointment(
            @PathVariable Long appointmentId,
            Authentication authentication
    ) {
        try {
            appointmentService.cancelAppointment(
                    appointmentId, authentication.getName()
            );
            return ResponseEntity.ok("Appointment cancelled successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}