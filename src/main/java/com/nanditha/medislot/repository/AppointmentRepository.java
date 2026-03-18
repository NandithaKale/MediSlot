package com.nanditha.medislot.repository;

import com.nanditha.medislot.model.Appointment;
import com.nanditha.medislot.model.Doctor;
import com.nanditha.medislot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByPatient(User patient);
    List<Appointment> findByDoctor(Doctor doctor);
    List<Appointment> findByDoctorAndAppointmentDate(Doctor doctor, LocalDate date);
    boolean existsByDoctorAndAppointmentDateAndAppointmentTime(
        Doctor doctor, LocalDate date, LocalTime time
    );
    List<Appointment> findByStatus(Appointment.Status status);
}