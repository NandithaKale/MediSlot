package com.nanditha.medislot.service;

import com.nanditha.medislot.model.Doctor;
import com.nanditha.medislot.model.User;
import com.nanditha.medislot.repository.DoctorRepository;
import com.nanditha.medislot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private UserRepository userRepository;

    public Doctor registerDoctor(String username, String specialization,
                                  String phone, String hospital,
                                  Integer experienceYears, Double consultationFee) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (doctorRepository.findByUser(user).isPresent()) {
            throw new RuntimeException("Doctor profile already exists");
        }

        Doctor doctor = new Doctor();
        doctor.setUser(user);
        doctor.setSpecialization(specialization);
        doctor.setPhone(phone);
        doctor.setHospital(hospital);
        doctor.setExperienceYears(experienceYears);
        doctor.setConsultationFee(consultationFee);
        doctor.setAvailable(true);

        return doctorRepository.save(doctor);
    }

    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    public List<Doctor> getDoctorsBySpecialization(String specialization) {
        return doctorRepository.findBySpecialization(specialization);
    }

    public List<Doctor> getAvailableDoctors() {
        return doctorRepository.findByAvailable(true);
    }

    public Doctor getDoctorById(Long id) {
        return doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
    }

    public Doctor getDoctorByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return doctorRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Doctor profile not found"));
    }

    public Doctor updateAvailability(String username, boolean available) {
        Doctor doctor = getDoctorByUsername(username);
        doctor.setAvailable(available);
        return doctorRepository.save(doctor);
    }
}