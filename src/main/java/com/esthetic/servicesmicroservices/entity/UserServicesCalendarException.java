package com.esthetic.servicesmicroservices.entity;

import com.esthetic.servicesmicroservices.dto.UserServicesCalendarExceptionDTO;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "tbl_user_services_calendar_exception")
public class UserServicesCalendarException {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "id_user")
    private String idUser;
    private String day;
    @Column(name = "start_time")
    private String startTime;
    @Column(name = "end_time")
    private String endTime;
    private int duration;
    @Column(name = "max_reservations")
    private int maxReservations;
    @Column(name = "is_active")
    private Boolean isActive;
    private String comments;
    @Column(name = "date_string")
    private LocalDateTime dateString;
    public UserServicesCalendarException(){} //Jpa lo requiere como default constructor
    public UserServicesCalendarException(UserServicesCalendarExceptionDTO userServicesCalendarExceptionDTO) {
        this.idUser = userServicesCalendarExceptionDTO.idUser;
        this.day = userServicesCalendarExceptionDTO.day;
        this.startTime = userServicesCalendarExceptionDTO.startTime;
        this.endTime = userServicesCalendarExceptionDTO.endTime;
        this.duration = userServicesCalendarExceptionDTO.duration;
        this.maxReservations = userServicesCalendarExceptionDTO.maxReservations;
        this.isActive = userServicesCalendarExceptionDTO.isActive;
        this.comments = userServicesCalendarExceptionDTO.comments;
        this.dateString = userServicesCalendarExceptionDTO.dateString;
    }
}
