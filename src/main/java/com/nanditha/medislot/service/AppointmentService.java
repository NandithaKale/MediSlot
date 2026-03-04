package com.nanditha.medislot.service;

import com.nanditha.medislot.model.*;
import com.nanditha.medislot.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AppointmentService {
    @Autowired private AppointmentRepository appointmentRepository;
    @Autowired private TimeSlotRepository timeSlotRepository;
    @Autowired private PatientRepository patientRepository;

    public Appointment bookAppointment(Long patientId, Long slotId) {
        if (appointmentRepository.existsBySlotId(slotId)) {
            throw new RuntimeException("Slot already booked");
        }
        TimeSlot slot = timeSlotRepository.findById(slotId)
                .orElseThrow(() -> new RuntimeException("Slot not found"));
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        slot.setStatus(TimeSlot.SlotStatus.BOOKED);
        timeSlotRepository.save(slot);

        Appointment appointment = new Appointment();
        appointment.setPatient(patient);
        appointment.setSlot(slot);
        appointment.setStatus("CONFIRMED");
        return appointmentRepository.save(appointment);
    }

    public List<Appointment> getMyAppointments(Long patientId) {
        return appointmentRepository.findByPatientId(patientId);
    }

    public void cancelAppointment(Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));
        appointment.getSlot().setStatus(TimeSlot.SlotStatus.AVAILABLE);
        timeSlotRepository.save(appointment.getSlot());
        appointmentRepository.delete(appointment);
    }
}
