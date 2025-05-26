package com.esthetic.servicesmicroservices.dto;

import com.esthetic.servicesmicroservices.entity.UserLocation;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class UserLocationDTO {
    public String id;
    public double latitude;
    public double longitude;
    public String idUser; // aux
    public Long idState;
    public Long idMunicipality;
    public String auxState;
    public String auxMunicipality;
    public String reference;

    public UserLocationDTO(UserLocation userLocation) {
        this.id = userLocation.getId();
        this.latitude = userLocation.getLatitude();
        this.longitude = userLocation.getLongitude();
        this.idState = userLocation.getIdState();
        this.idMunicipality = userLocation.getIdMunicipality();
        this.auxState = userLocation.getAuxState();
        this.auxMunicipality = userLocation.getAuxMunicipality();
        this.reference = userLocation.getReference();
    }
}

