package com.esthetic.servicesmicroservices.entity;

import com.esthetic.servicesmicroservices.dto.ScheduleServiceDTO;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "tbl_schedule_service")
public class ScheduleService {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "id_client")
    private String idClient;
    @Column(name = "id_provider")
    private String idProvider;
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
    public ScheduleService(ScheduleServiceDTO scheduleServiceDTO) {
        this.idClient = scheduleServiceDTO.idClient;
        this.idProvider = scheduleServiceDTO.idProvider;
        this.scheduleDate = scheduleServiceDTO.scheduleDate;
        this.startTime = scheduleServiceDTO.startTime;
        this.endTime = scheduleServiceDTO.endTime;
        this.nameService = scheduleServiceDTO.nameService;
        this.people = scheduleServiceDTO.people;
        this.amount = scheduleServiceDTO.amount;
    }
}
