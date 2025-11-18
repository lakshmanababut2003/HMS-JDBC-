package dto.response;

import java.time.LocalDate;

public class LabReportRes {
    private int reportId;
    private String labTestName;
    private int appointmentId;
    private String labTechicianUserName;
    private String testResult;
    private LocalDate createdAt;
    private String status;

    public LabReportRes(int reportId, String labTestName, int appointmentId, String labTechicianUserName,
            String testResult, LocalDate createdAt, String status) {

        this.reportId = reportId;
        this.labTestName = labTestName;
        this.appointmentId = appointmentId;
        this.labTechicianUserName = labTechicianUserName;
        this.testResult = testResult;
        this.createdAt = createdAt;
        this.status = status;
    }

    public int getReportId() {
        return reportId;
    }

    public String getLabTestName() {
        return labTestName;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public String getLabTechnicianUserName() {
        return labTechicianUserName;
    }

    public String getTestResult() {
        return testResult;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public String getStatus() {
        return status;
    }
}