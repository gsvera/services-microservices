package com.esthetic.servicesmicroservices.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.time.Instant;

@Entity @Data @Table(name = "tbl_provider_ratings")
public class ProviderRatings {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "id_user", insertable = true, updatable = true, nullable = false)
    @JsonBackReference
    private User idUser;
    @OneToOne
    @JoinColumn(name = "id_provider", insertable = true, updatable = true, nullable = false)
    @JsonBackReference
    private User idProvider;
    private Integer rating = 0;
    private String comment;
    @Column(name = "created_at")
    private Timestamp createdAt;
    @Column(name = "updated_at")
    private Timestamp updateAt;
    private Boolean show = false;
    @Column(name = "id_service")
    private Long idService;
    @Column(name = "is_pending")
    private Boolean isPending = true;
    public ProviderRatings(){} // default constructor
    public ProviderRatings(String idUser, String idProvider, Long idService) {
        this.idUser =  new User(idUser);
        this.idService = idService;
        this.createdAt = Timestamp.from(Instant.now());
        this.idProvider =  new User(idProvider);
    }
}
