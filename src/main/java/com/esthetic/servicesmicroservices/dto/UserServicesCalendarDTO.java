package com.esthetic.servicesmicroservices.dto;

import com.esthetic.servicesmicroservices.entity.UserServicesCalendar;

public class UserServicesCalendarDTO {
    public Long id;
    public String day;
    public String startTime;
    public String endTime;
    public int duration;
    public int maxReservations;
    public String idUser;
    public UserServicesCalendarDTO(){} // Se requiere para recibir los datos del front
    public UserServicesCalendarDTO(UserServicesCalendar userServicesCalendar) {
        this.id = userServicesCalendar.getId();
        this.day = userServicesCalendar.getDay();
        this.startTime = userServicesCalendar.getStartTime();
        this.endTime = userServicesCalendar.getEndTime();
        this.duration = userServicesCalendar.getDuration();
        this.maxReservations = userServicesCalendar.getMaxReservations();
        this.idUser = userServicesCalendar.getIdUser();
    }
}
