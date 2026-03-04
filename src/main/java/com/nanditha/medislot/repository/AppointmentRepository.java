package com.nanditha.medislot.repository;

import com.nanditha.medislot.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByPatientId(Long patientId);
    boolean existsBySlotId(Long slotId);
}
