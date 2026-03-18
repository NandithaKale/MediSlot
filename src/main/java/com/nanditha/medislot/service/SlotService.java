package com.nanditha.medislot.service;

import com.nanditha.medislot.dto.SlotRequest;
import com.nanditha.medislot.model.Doctor;
import com.nanditha.medislot.model.Slot;
import com.nanditha.medislot.repository.SlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class SlotService {

    @Autowired
    private SlotRepository slotRepository;

    @Autowired
    private DoctorService doctorService;

    public Slot addSlot(String username, SlotRequest request) {
        Doctor doctor = doctorService.getDoctorByUsername(username);

        Slot slot = new Slot();
        slot.setDoctor(doctor);
        slot.setSlotDate(request.getSlotDate());
        slot.setStartTime(request.getStartTime());
        slot.setEndTime(request.getEndTime());
        slot.setBooked(false);

        return slotRepository.save(slot);
    }

    public List<Slot> getSlotsByDoctor(String username) {
        Doctor doctor = doctorService.getDoctorByUsername(username);
        return slotRepository.findByDoctor(doctor);
    }

    public List<Slot> getAvailableSlotsByDoctorAndDate(Long doctorId, LocalDate date) {
        Doctor doctor = doctorService.getDoctorById(doctorId);
        return slotRepository.findByDoctorAndSlotDateAndBooked(doctor, date, false);
    }

    public List<Slot> getAllSlotsByDoctorAndDate(Long doctorId, LocalDate date) {
        Doctor doctor = doctorService.getDoctorById(doctorId);
        return slotRepository.findByDoctorAndSlotDate(doctor, date);
    }

    public void deleteSlot(Long slotId, String username) {
        Slot slot = slotRepository.findById(slotId)
                .orElseThrow(() -> new RuntimeException("Slot not found"));

        Doctor doctor = doctorService.getDoctorByUsername(username);

        if (!slot.getDoctor().getId().equals(doctor.getId())) {
            throw new RuntimeException("Unauthorized to delete this slot");
        }

        if (slot.isBooked()) {
            throw new RuntimeException("Cannot delete a booked slot");
        }

        slotRepository.delete(slot);
    }

    public Slot markSlotAsBooked(Long slotId) {
        Slot slot = slotRepository.findById(slotId)
                .orElseThrow(() -> new RuntimeException("Slot not found"));
        slot.setBooked(true);
        return slotRepository.save(slot);
    }
}