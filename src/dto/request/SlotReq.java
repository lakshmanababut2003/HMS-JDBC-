package dto.request;

import java.time.LocalDate;
import java.time.LocalTime;

public class SlotReq {

    private String docName;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private int duration;

    public SlotReq( String docName, LocalDate date, LocalTime startTime, LocalTime endTime, int duration) {
        this.docName = docName;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.duration = duration;
    }

    public String getDoctor() {
        return docName;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public int getDuration() {
        return duration;
    }
}
