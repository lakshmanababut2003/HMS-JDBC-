package dto.request;

public class LabReportReq {
    private String labTestName;
    private int appointmentId;
    private String labTechnicianUsername; 
    private String result;

    public LabReportReq(String labTestName, int appointmentId, String labTechnicianUsername) {
        this.labTestName = labTestName;
        this.appointmentId = appointmentId;
        this.labTechnicianUsername = labTechnicianUsername;
    }

      public LabReportReq(String labTestName, int appointmentId, String labTechnicianUsername , String result) {
        this(labTestName, appointmentId, labTechnicianUsername);
        this.result = result;
    }

    public String getResult(){
        return result;
    }


    public String getLabTestName() {
        return labTestName;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public String getLabTechnicianUsername() {
        return labTechnicianUsername;
    }
}