package com.esthetic.servicesmicroservices.dto;

import java.time.LocalDateTime;

public class ScheduleServiceDTO {
    public Long id;
    public String idClient;
    public String idProvider;
    public LocalDateTime scheduleDate;
    public String startTime;
    public String endTime;
    public String nameService;
    public int people;
    public Double amount;
}
