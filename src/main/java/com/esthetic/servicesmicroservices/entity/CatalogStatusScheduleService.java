package com.esthetic.servicesmicroservices.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "tbl_catalog_status_schedule_service")
public class CatalogStatusScheduleService {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "order_number")
    private int orderNumber;
    @Column(name = "status_name")
    private String statusName;
    @Column(name = "status_value")
    private String statusValue;
    @Column(name = "status_done")
    private Boolean statusDone;
}
