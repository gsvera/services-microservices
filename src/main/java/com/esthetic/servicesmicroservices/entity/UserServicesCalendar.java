package com.esthetic.servicesmicroservices.entity;

import com.esthetic.servicesmicroservices.dto.UserServicesCalendarDTO;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "tbl_user_services_calendar")
public class UserServicesCalendar {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String day;
    @Column(name = "start_time")
    private String startTime;
    @Column(name = "end_time")
    private String endTime;
    private int duration;
    @Column(name = "max_reservations")
    private int maxReservations;
    @Column(name = "id_user")
    private String idUser;
    public UserServicesCalendar(){} // Jpa lo requiere como default constructor
    public UserServicesCalendar(UserServicesCalendarDTO userServicesCalendarDTO) {
        this.day = userServicesCalendarDTO.day;
        this.startTime = userServicesCalendarDTO.startTime;
        this.endTime = userServicesCalendarDTO.endTime;
        this.duration = userServicesCalendarDTO.duration;
        this.maxReservations = userServicesCalendarDTO.maxReservations;
        this.idUser = userServicesCalendarDTO.idUser;
    }
}
