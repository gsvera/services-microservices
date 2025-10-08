package com.esthetic.servicesmicroservices.entity;

import jakarta.persistence.*;
import lombok.Data;

@Table(name = "tbl_temp_client")
@Entity
@Data
public class TempClient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "temp_name_client")
    private String tempNameClient;
    @Column(name = "temp_lada_client")
    private String tempLadaClient;
    @Column(name = "temp_phone_client")
    private String tempPhoneClient;
    @Column(name = "id_provider")
    private String idProvider;
    public TempClient(){} // Constructor auxiliar
    public TempClient(String idProvider, String tempNameClient, String tempLadaClient, String tempPhoneClient) {
        this.idProvider = idProvider;
        this.tempNameClient = tempNameClient;
        this.tempLadaClient = tempLadaClient;
        this.tempPhoneClient = tempPhoneClient;
    }
}
