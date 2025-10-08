package com.esthetic.servicesmicroservices.entity;

import com.esthetic.servicesmicroservices.dto.ScheduleServiceDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "tbl_schedule_service")
public class ScheduleService {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "id_client", insertable = true, updatable = true, nullable = true)
    @NotFound(action = NotFoundAction.IGNORE)
    @JsonBackReference
    private User idClient;
    @OneToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "id_provider", insertable = true, updatable = true, nullable = false)
    @JsonBackReference
    private User idProvider;
    @OneToOne
    @JoinColumn(name = "id_provider", referencedColumnName = "id_user",insertable = false, updatable = false, nullable = false)
    @JsonBackReference
    private UserLocation userLocation;
    @Column(name = "schedule_date")
    private LocalDateTime scheduleDate;
    @Column(name = "start_time")
    private String startTime;
    @Column(name = "end_time")
    private String endTime;
    @Column(name = "name_service")
    private String nameService;
    private int people;
    private Double amount;
    @Column(name = "status_service")
    private int statusService;
    private String comments;
    @Column(name = "created_at")
    private Timestamp createdAt;
    @Column(name = "temp_name_client")
    private String tempNameClient;
    @Column(name = "temp_lada_client")
    private String tempLadaClient;
    @Column(name = "temp_phone_client")
    private String tempPhoneClient;
    public ScheduleService(){} // default constructor
    public ScheduleService(ScheduleServiceDTO scheduleServiceDTO) {
        this.scheduleDate = scheduleServiceDTO.scheduleDate;
        this.startTime = scheduleServiceDTO.startTime;
        this.endTime = scheduleServiceDTO.endTime;
        this.nameService = scheduleServiceDTO.nameService;
        this.people = scheduleServiceDTO.people;
        this.amount = scheduleServiceDTO.amount;
        this.createdAt = scheduleServiceDTO.createdAt;
        this.tempNameClient = scheduleServiceDTO.tempNameClient;
        this.tempLadaClient = scheduleServiceDTO.tempLadaClient;
        this.tempPhoneClient = scheduleServiceDTO.tempPhoneClient;
        this.statusService = scheduleServiceDTO.statusService;
        this.idProvider = new User(scheduleServiceDTO.idProviderAux);
    }
    public ScheduleService(ScheduleServiceDTO scheduleServiceDTO, User user) {
        this.scheduleDate = scheduleServiceDTO.scheduleDate;
        this.startTime = scheduleServiceDTO.startTime;
        this.endTime = scheduleServiceDTO.endTime;
        this.nameService = scheduleServiceDTO.nameService;
        this.people = scheduleServiceDTO.people;
        this.amount = scheduleServiceDTO.amount;
        this.createdAt = scheduleServiceDTO.createdAt;
        this.idClient = user;
        this.idProvider = new User(scheduleServiceDTO.idProviderAux);
    }
}
