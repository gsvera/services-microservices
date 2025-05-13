package com.esthetic.servicesmicroservices.dto;

import com.esthetic.servicesmicroservices.entity.User;

public class UserDTO {
    public String id;
    public String firstName;
    public String lastName;
    public String email;
    public String lada;
    public String phone;
    public UserDTO(User user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.lada = user.getLada();
        this.phone = user.getPhone();
    }
}
