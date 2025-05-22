package com.esthetic.servicesmicroservices.dto;

public class TimeSlotDTO {
    public String start;
    public String end;
    public TimeSlotDTO(String startTime, String endTime) {
        this.start = startTime;
        this.end = endTime;
    }
}
