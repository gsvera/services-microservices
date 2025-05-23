package com.esthetic.servicesmicroservices.dto;

import com.esthetic.servicesmicroservices.entity.ScheduleService;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class ScheduleServiceDTO {
    public Long id;
    public UserDTO idClient;
    public String idClientAux;
    public String idProvider;
    public LocalDateTime scheduleDate;
    public String startTime;
    public String endTime;
    public String nameService;
    public int people;
    public Double amount;
    public int statusService;
    public String commentReject;
    public Timestamp createdAt;
    public ScheduleServiceDTO(){} // default constructor
    public ScheduleServiceDTO(ScheduleService scheduleService) {
        this.id = scheduleService.getId();
        this.idClient = new UserDTO(scheduleService.getIdClient());
        this.idProvider = scheduleService.getIdProvider();
        this.scheduleDate = scheduleService.getScheduleDate();
        this.startTime = scheduleService.getStartTime();
        this.endTime = scheduleService.getEndTime();
        this.nameService = scheduleService.getNameService();
        this.people = scheduleService.getPeople();
        this.amount = scheduleService.getAmount();
        this.statusService = scheduleService.getStatusService();
        this.commentReject = scheduleService.getCommentReject();
    }
}
