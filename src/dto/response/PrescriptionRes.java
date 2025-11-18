package dto.response;

import java.time.LocalDate;

public class PrescriptionRes {
    
    private int prescriptionId;
    private int appointmentId;
    private String diagnosis;
    private String medications;
    private String instructions;
    private LocalDate prescribedDate;

    public PrescriptionRes(int prescriptionId, int appointmentId, String diagnosis,
            String medications, String instructions, LocalDate prescribedDate) {

        this.prescriptionId = prescriptionId;
        this.appointmentId = appointmentId;
        this.diagnosis = diagnosis;
        this.medications = medications;
        this.instructions = instructions;
        this.prescribedDate = prescribedDate;
    }

    public int getPrescriptionId() {
        return prescriptionId;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public String getMedications() {
        return medications;
    }

    public String getInstructions() {
        return instructions;
    }

    public LocalDate getPrescribedDate() {
        return prescribedDate;
    }
}