package com.nanditha.medislot.repository;

import com.nanditha.medislot.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {
}
