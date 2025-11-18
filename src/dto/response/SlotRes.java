package dto.response;

import java.time.LocalDate;
import java.time.LocalTime;

import models.SlotStatus;

public class SlotRes {
    private int slotId;
    private String doctorName;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private SlotStatus status;
    private int count;
    private String message;

    public SlotRes(String doctorName, LocalDate date, LocalTime startTime, LocalTime endTime,
            SlotStatus status, int count, String message) {
        this.doctorName = doctorName;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
        this.count = count;
        this.message = message;
    }

    public SlotRes(int slotId, String doctorName, LocalDate date, LocalTime startTime,
            LocalTime endTime, SlotStatus status) {

        this.slotId = slotId;
        this.doctorName = doctorName;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
    }

    public int getSlotId() {
        return slotId;
    }

    public String getDoctorName() {
        return doctorName;
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

    public int getCount() {
        return count;
    }

    public String getMessage() {
        return message;
    }
}