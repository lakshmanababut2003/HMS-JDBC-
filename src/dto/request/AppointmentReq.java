package dto.request;

import java.time.LocalDate;
import java.time.LocalTime;

public class AppointmentReq {
    private String docName;
    private String patName;
    private LocalDate date;
    private LocalTime time;

    public AppointmentReq(String docName, String patName, LocalDate date, LocalTime time) {
        this.docName = docName;
        this.patName = patName;
        this.date = date;
        this.time = time;
    }

    
    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public String getPatName() {
        return patName;
    }

    public void setPatName(String patName) {
        this.patName = patName;
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


}