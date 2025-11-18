package dto.request;

public class PrescriptionReq {
    private int appointmentId;
    private String diagnosis;
    private String medications;
    private String instructions;

    public PrescriptionReq(int appointmentId, String diagnosis, String medications, String instructions) {
        this.appointmentId = appointmentId;
        this.diagnosis = diagnosis;
        this.medications = medications;
        this.instructions = instructions;
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
}