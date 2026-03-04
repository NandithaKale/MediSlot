package com.nanditha.medislot.repository;

import com.nanditha.medislot.model.TimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TimeSlotRepository extends JpaRepository<TimeSlot, Long> {
    List<TimeSlot> findByDoctorIdAndStatus(Long doctorId, TimeSlot.SlotStatus status);
}
