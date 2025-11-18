package models;

import java.time.LocalDate;

public class Prescription {


    private int id;
    private int appointmentId;
    private String diagnosis;
    private String medications;
    private String instructions;
    private LocalDate prescribedDate;

    public Prescription(int appointmentId, String diagnosis, String medications, String instructions) {

        this.appointmentId = appointmentId;
        this.diagnosis = diagnosis;
        this.medications = medications;
        this.instructions = instructions;
        this.prescribedDate = LocalDate.now();
    }

    public Prescription(int id, int appointmentId, String diagnosis, String medications,
            String instructions, LocalDate prescribedDate) {
        this(appointmentId, diagnosis, medications, instructions);
        this.id = id;
        this.prescribedDate = prescribedDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getMedications() {
        return medications;
    }

    public void setMedications(String medications) {
        this.medications = medications;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public LocalDate getPrescribedDate() {
        return prescribedDate;
    }

    public void setPrescribedDate(LocalDate prescribedDate) {
        this.prescribedDate = prescribedDate;
    }
}