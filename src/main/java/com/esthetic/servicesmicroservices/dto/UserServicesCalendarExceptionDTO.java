package com.esthetic.servicesmicroservices.dto;

import com.esthetic.servicesmicroservices.entity.UserServicesCalendarException;

import java.time.LocalDateTime;

public class UserServicesCalendarExceptionDTO {
    public Long id;
    public String idUser;
    public String day;
    public String startTime;
    public String endTime;
    public int duration;
    public int maxReservations;
    public Boolean isActive;
    public String comments;
    public LocalDateTime dateString;
    public UserServicesCalendarExceptionDTO(){} // Constructor default
    public UserServicesCalendarExceptionDTO(UserServicesCalendarException userServicesCalendarException) {
        this.id = userServicesCalendarException.getId();
        this.idUser = userServicesCalendarException.getIdUser();
        this.day = userServicesCalendarException.getDay();
        this.startTime = userServicesCalendarException.getStartTime();
        this.endTime = userServicesCalendarException.getEndTime();
        this.duration = userServicesCalendarException.getDuration();
        this.maxReservations = userServicesCalendarException.getMaxReservations();
        this.isActive = userServicesCalendarException.getIsActive();
        this.comments = userServicesCalendarException.getComments();
        this.dateString = userServicesCalendarException.getDateString();
    }
}
