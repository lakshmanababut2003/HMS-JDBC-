package models;

import java.time.LocalDate;

public class LabReport {

    private int id;
    private int labTestId;
    private int appointmentId;
    private int labTechicianId;
    private String testResult;
    private LocalDate createdAt;
    private LabReportStatus status = LabReportStatus.PENDING;

    public LabReport( int labTestId, int appointmentId, int labTechicianId ,String testResult,
            LocalDate createdAt , LabReportStatus status) {
     
        this.labTestId = labTestId;
        this.appointmentId = appointmentId;
        this.labTechicianId = labTechicianId;
        this.createdAt = createdAt;
        this.status = status;
        this.testResult = testResult;
    }

    public LabReport(int id , int labTestId, int appointmentId, int labTechicianId ,String testResult,
            LocalDate createdAt , LabReportStatus status) {
     
       this(labTestId, appointmentId, labTechicianId, testResult, createdAt, status);
       this.id = id;
    }

    public int getLabTestId() {
        return labTestId;
    }

    public int getAppointmentId() {
        return appointmentId;
    }


    public int getLabTechicianId() {
        return labTechicianId;
    }

    public String getTestResult() {
        return testResult;
    }


    public void setTestResult(String testResult) {
        this.testResult = testResult;
    }


    public LocalDate getCreatedAt() {
        return createdAt;
    }
 
    public LabReportStatus getStatus() {
        return status;
    }


    public void setStatus(LabReportStatus status) {
        this.status = status;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }


    

    
}
