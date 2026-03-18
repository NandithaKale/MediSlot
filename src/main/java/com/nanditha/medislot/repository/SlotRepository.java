package com.nanditha.medislot.repository;

import com.nanditha.medislot.model.Doctor;
import com.nanditha.medislot.model.Slot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface SlotRepository extends JpaRepository<Slot, Long> {
    List<Slot> findByDoctor(Doctor doctor);
    List<Slot> findByDoctorAndSlotDate(Doctor doctor, LocalDate date);
    List<Slot> findByDoctorAndSlotDateAndBooked(Doctor doctor, LocalDate date, boolean booked);
    List<Slot> findByDoctorAndBooked(Doctor doctor, boolean booked);
}