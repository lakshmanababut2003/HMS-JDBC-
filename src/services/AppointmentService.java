package services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import dto.request.AppointmentReq;
import dto.response.AppointmentRes;
import exceptions.AlreadyExistsException;
import exceptions.CRUDFailedException;
import exceptions.NotFoundException;
import exceptions.ServiceException;
import models.Appointment;
import models.Doctor;
import models.Patient;
import models.Slot;
import models.SlotStatus;
import repo.AppointmentRepo;
import repo.DoctorRepo;
import repo.PatientRepo;
import repo.SlotRepo;

public class AppointmentService {

    private final AppointmentRepo appointmentRepo;
    private final DoctorRepo doctorRepo;
    private final PatientRepo patientRepo;
    private final SlotRepo slotRepo;

    public AppointmentService() {
        this.appointmentRepo = AppointmentRepo.getAppointmentRepo();
        this.doctorRepo = DoctorRepo.getDoctorRepo();
        this.patientRepo = PatientRepo.getPatientRepo();
        this.slotRepo = SlotRepo.getSlotRepo();
    }

    public AppointmentRes createAppointment(AppointmentReq req) {
        try {

            Doctor doctor = getDoctorByUsername(req.getDocName());
            if (doctor == null) {
                throw new NotFoundException("Doctor not found: " + req.getDocName());
            }


            Patient patient = getPatientByUsername(req.getPatName());
            if (patient == null) {
                throw new NotFoundException("Patient not found: " + req.getPatName());
            }

            Slot availableSlot = findAvailableSlot(doctor.getId(), req.getDate(), req.getTime());
            if (availableSlot == null) {
                throw new ServiceException("No available slot found for the selected time");
            }


            if (hasExistingAppointment(doctor.getId(), patient.getId(), req.getDate(), req.getTime())) {
                throw new AlreadyExistsException(
                        "Appointment already exists for this patient with the doctor at the selected time");
            }

            Appointment appointment = new Appointment(
                    doctor.getId(),
                    patient.getId(),
                    availableSlot.getSlotId(),
                    req.getDate(),
                    req.getTime());

            availableSlot.setStatus(SlotStatus.BOOKED);
            slotRepo.update(availableSlot);

            
            Appointment savedAppointment = appointmentRepo.add(appointment);

            return new AppointmentRes(
                    savedAppointment.getId(),
                    doctor.getUsername(),
                    patient.getUsername(),
                    savedAppointment.getDate(),
                    savedAppointment.getTime(),
                    savedAppointment.getStatus().name());

        } catch (CRUDFailedException e) {
            throw new ServiceException("Failed to create appointment: " + e.getMessage());
        }
    }

    public List<AppointmentRes> getAllAppointments() {
        List<Appointment> appointments = appointmentRepo.getAll();
        if (appointments.isEmpty()) {
            throw new NotFoundException("No appointments found");
        }

        List<AppointmentRes> result = new ArrayList<>();
        for (Appointment appointment : appointments) {
            Doctor doctor = doctorRepo.getById(appointment.getDoctorId());
            Patient patient = patientRepo.getById(appointment.getPatientId());

            if (doctor != null && patient != null) {
                result.add(new AppointmentRes(
                        appointment.getId(),
                        doctor.getUsername(),
                        patient.getUsername(),
                        appointment.getDate(),
                        appointment.getTime(),
                        appointment.getStatus().name()));
            }
        }

        return result;
    }

    public List<AppointmentRes> getAppointmentsByPatient(String patientUsername) {
        Patient patient = getPatientByUsername(patientUsername);
        if (patient == null) {
            throw new NotFoundException("Patient not found: " + patientUsername);
        }

        List<Appointment> appointments = appointmentRepo.findByPatient(patient.getId());
        if (appointments.isEmpty()) {
            throw new NotFoundException("No appointments found for patient: " + patientUsername);
        }

        List<AppointmentRes> result = new ArrayList<>();
        for (Appointment appointment : appointments) {
            Doctor doctor = doctorRepo.getById(appointment.getDoctorId());
            if (doctor != null) {
                result.add(new AppointmentRes(
                        appointment.getId(),
                        doctor.getUsername(),
                        patient.getUsername(),
                        appointment.getDate(),
                        appointment.getTime(),
                        appointment.getStatus().name()
                ));
            }
        }

        return result;
    }

    private Doctor getDoctorByUsername(String username) {
        for (Doctor doctor : doctorRepo.getAll()) {
            if (doctor.getUsername().equalsIgnoreCase(username)) {
                return doctor;
            }
        }
        return null;
    }

    private Patient getPatientByUsername(String username) {
        for (Patient patient : patientRepo.getAll()) {
            if (patient.getUsername().equalsIgnoreCase(username)) {
                return patient;
            }
        }
        return null;
    }

    private Slot findAvailableSlot(int doctorId, LocalDate date, LocalTime time) {
        List<Slot> availableSlots = slotRepo.findAvailableSlotsByDoctorAndDate(doctorId, date);
        for (Slot slot : availableSlots) {
            if (slot.getStartTime().equals(time)) {
                return slot;
            }
        }
        return null;
    }

    private boolean hasExistingAppointment(int doctorId, int patientId, LocalDate date, LocalTime time) {
        List<Appointment> doctorAppointments = appointmentRepo.findByDoctorAndDate(doctorId, date);
        for (Appointment appointment : doctorAppointments) {
            if (appointment.getPatientId() == patientId && appointment.getTime().equals(time)) {
                return true;
            }
        }
        return false;
    }
}