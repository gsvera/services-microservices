package com.esthetic.servicesmicroservices.dto;

import com.esthetic.servicesmicroservices.entity.ProviderRatings;

import java.sql.Timestamp;

public class ProviderRatingsDTO {
    public Long id;
    public String idUser;
    public UserDTO idProvider;
    public Integer rating;
    public String comment;

    public Timestamp createdAt;
    public Boolean show;
    public Long idService;
    public Boolean isPending;
    public String nameClient;
    public ProviderRatingsDTO(){} // default constructor
    public ProviderRatingsDTO(ProviderRatings providerRatings){
        this.id = providerRatings.getId();
        this.idUser = providerRatings.getIdUser().getId();
        this.rating = providerRatings.getRating();
        this.comment = providerRatings.getComment();
        this.createdAt = providerRatings.getCreatedAt();
        this.show = providerRatings.getShow();
        this.idService = providerRatings.getIdService();
        this.isPending = providerRatings.getIsPending();
        this.idProvider = new UserDTO(providerRatings.getIdProvider());
    }
    public ProviderRatingsDTO(Long id, Integer rating, String comment, Timestamp createdAt, String firstName, String lastName) {
        this.id = id;
        this.rating = rating;
        this.comment = comment;
        this.createdAt = createdAt;
        this.nameClient = firstName + " " + lastName;
    }
}
