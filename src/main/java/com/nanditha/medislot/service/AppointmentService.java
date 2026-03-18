package com.nanditha.medislot.service;

import com.nanditha.medislot.dto.AppointmentRequest;
import com.nanditha.medislot.model.Appointment;
import com.nanditha.medislot.model.Doctor;
import com.nanditha.medislot.model.User;
import com.nanditha.medislot.repository.AppointmentRepository;
import com.nanditha.medislot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DoctorService doctorService;

    public Appointment bookAppointment(String username, AppointmentRequest request) {
        User patient = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Doctor doctor = doctorService.getDoctorById(request.getDoctorId());

        boolean conflict = appointmentRepository
                .existsByDoctorAndAppointmentDateAndAppointmentTime(
                        doctor,
                        request.getAppointmentDate(),
                        request.getAppointmentTime()
                );

        if (conflict) {
            throw new RuntimeException(
                    "This time slot is already booked. Please choose another time."
            );
        }

        Appointment appointment = new Appointment();
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setAppointmentDate(request.getAppointmentDate());
        appointment.setAppointmentTime(request.getAppointmentTime());
        appointment.setReason(request.getReason());
        appointment.setStatus(Appointment.Status.PENDING);

        return appointmentRepository.save(appointment);
    }

    public List<Appointment> getPatientAppointments(String username) {
        User patient = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return appointmentRepository.findByPatient(patient);
    }

    public List<Appointment> getDoctorAppointments(String username) {
        Doctor doctor = doctorService.getDoctorByUsername(username);
        return appointmentRepository.findByDoctor(doctor);
    }

    public Appointment updateAppointmentStatus(Long appointmentId,
                                                Appointment.Status status,
                                                String username) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        Doctor doctor = doctorService.getDoctorByUsername(username);

        if (!appointment.getDoctor().getId().equals(doctor.getId())) {
            throw new RuntimeException("Unauthorized to update this appointment");
        }

        appointment.setStatus(status);
        return appointmentRepository.save(appointment);
    }

    public void cancelAppointment(Long appointmentId, String username) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        User patient = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!appointment.getPatient().getId().equals(patient.getId())) {
            throw new RuntimeException("Unauthorized to cancel this appointment");
        }

        appointment.setStatus(Appointment.Status.CANCELLED);
        appointmentRepository.save(appointment);
    }

    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }
}