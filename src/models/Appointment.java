package models;

import java.time.LocalDate;
import java.time.LocalTime;

public class Appointment {
    private int id;
    private int doctorId;
    private int patientId;
    private int slotId;
    private LocalDate date;
    private LocalTime time;
    private AppointmentStatus status;

    public Appointment(int doctorId, int patientId, int slotId, LocalDate date, LocalTime time) {
        this.doctorId = doctorId;
        this.patientId = patientId;
        this.slotId = slotId;
        this.date = date;
        this.time = time;
        this.status = AppointmentStatus.BOOKED;
    }

    public Appointment(int id, int doctorId, int patientId, int slotId, LocalDate date, LocalTime time,
            AppointmentStatus status) {
        this(doctorId, patientId, slotId, date, time);
        this.id = id;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public int getSlotId() {
        return slotId;
    }

    public void setSlotId(int slotId) {
        this.slotId = slotId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }


}