package dto.response;

import java.time.LocalDate;
import java.time.LocalTime;

public class AppointmentRes {
    
    private int appointmentId;
    private String doctorName;
    private String patientName;
    private LocalDate date;
    private LocalTime time;
    private String status;

    public AppointmentRes(int appointmentId, String doctorName, String patientName,
            LocalDate date, LocalTime time, String status) {
        this.appointmentId = appointmentId;
        this.doctorName = doctorName;
        this.patientName = patientName;
        this.date = date;
        this.time = time;
        this.status = status;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public String getPatientName() {
        return patientName;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public String getStatus() {
        return status;
    }

   
}