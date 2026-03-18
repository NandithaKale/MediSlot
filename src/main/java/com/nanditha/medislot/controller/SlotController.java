package com.nanditha.medislot.controller;

import com.nanditha.medislot.dto.SlotRequest;
import com.nanditha.medislot.model.Slot;
import com.nanditha.medislot.service.SlotService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/slots")
@CrossOrigin(origins = "*")
public class SlotController {

    @Autowired
    private SlotService slotService;

    @PostMapping
    public ResponseEntity<?> addSlot(
            Authentication authentication,
            @Valid @RequestBody SlotRequest request
    ) {
        try {
            Slot slot = slotService.addSlot(authentication.getName(), request);
            return ResponseEntity.ok(slot);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/my")
    public ResponseEntity<?> getMySlots(Authentication authentication) {
        try {
            List<Slot> slots = slotService.getSlotsByDoctor(authentication.getName());
            return ResponseEntity.ok(slots);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/available")
    public ResponseEntity<?> getAvailableSlots(
            @RequestParam Long doctorId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        try {
            List<Slot> slots = slotService.getAvailableSlotsByDoctorAndDate(doctorId, date);
            return ResponseEntity.ok(slots);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllSlotsByDoctorAndDate(
            @RequestParam Long doctorId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        try {
            List<Slot> slots = slotService.getAllSlotsByDoctorAndDate(doctorId, date);
            return ResponseEntity.ok(slots);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{slotId}")
    public ResponseEntity<?> deleteSlot(
            @PathVariable Long slotId,
            Authentication authentication
    ) {
        try {
            slotService.deleteSlot(slotId, authentication.getName());
            return ResponseEntity.ok("Slot deleted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}