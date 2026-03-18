package com.nanditha.medislot.repository;

import com.nanditha.medislot.model.Doctor;
import com.nanditha.medislot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    List<Doctor> findBySpecialization(String specialization);
    List<Doctor> findByAvailable(boolean available);
    Optional<Doctor> findByUser(User user);
    List<Doctor> findBySpecializationAndAvailable(String specialization, boolean available);
}