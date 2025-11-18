package models;

import java.time.LocalDate;
import java.time.LocalTime;

public class Slot {

    private int id;
    private int doctorId;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private SlotStatus status = SlotStatus.FREE;

    public Slot(int doctorId , LocalDate date , LocalTime startTime , LocalTime endTime){

        this.doctorId = doctorId;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Slot(int id ,int doctorId , LocalDate date , LocalTime startTime , LocalTime endTime , SlotStatus status ){
        this(doctorId, date, startTime, endTime);
        this.id = id;
        this.status = status;
    }

    public int getSlotId(){
        return this.id;
    }

    public void setSlotId(int id){
        this.id = id;
    }

    public int getDoctorId() {
        return doctorId;
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

    public SlotStatus getStatus() {
        return status;
    }

    public void setStatus(SlotStatus status) {
        this.status = status;
    }

 
    
    
    
}
