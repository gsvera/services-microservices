package com.esthetic.servicesmicroservices.dto;

import com.esthetic.servicesmicroservices.entity.TempClient;

public class TempClientDTO {
    public Long id;
    public String tempNameClient;
    public String tempLadaClient;
    public String tempPhoneClient;
    public String idProvider;
    public TempClientDTO(TempClient tempClient) {
        this.id = tempClient.getId();
        this.tempNameClient = tempClient.getTempNameClient();
        this.tempLadaClient = tempClient.getTempLadaClient();
        this.tempPhoneClient = tempClient.getTempPhoneClient();
        this.idProvider = tempClient.getIdProvider();
    }
}
