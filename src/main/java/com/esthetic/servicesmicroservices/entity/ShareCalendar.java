package com.esthetic.servicesmicroservices.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;

@Entity
@Data
@Table(name = "tbl_share_calendar")
public class ShareCalendar {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "id_provider")
    private String idProvider;
    private String token;
    @Column(name = "created_at")
    private Instant createdAt;
    public ShareCalendar(){} // default constructor
    public ShareCalendar(String idProvider, String token, Instant createdAt) {
        this.idProvider = idProvider;
        this.token = token;
        this.createdAt = createdAt;
    }
}
