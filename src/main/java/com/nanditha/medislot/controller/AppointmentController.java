package com.nanditha.medislot.controller;

import com.nanditha.medislot.model.Appointment;
import com.nanditha.medislot.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {
    @Autowired
    private AppointmentService appointmentService;

    @PostMapping("/book")
    public ResponseEntity<Appointment> book(@RequestParam Long patientId,
                                             @RequestParam Long slotId) {
        return ResponseEntity.ok(appointmentService.bookAppointment(patientId, slotId));
    }

    @GetMapping("/my/{patientId}")
    public ResponseEntity<List<Appointment>> myAppointments(@PathVariable Long patientId) {
        return ResponseEntity.ok(appointmentService.getMyAppointments(patientId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> cancel(@PathVariable Long id) {
        appointmentService.cancelAppointment(id);
        return ResponseEntity.ok("Appointment cancelled successfully");
    }
}
