package com.esthetic.servicesmicroservices.dto;

import com.esthetic.servicesmicroservices.entity.MenuService;

public class MenuServiceDTO {
    public Long id;
    public String idUser;
    public String nameService;
    public Double price;
    public Integer people;
    public MenuServiceDTO(){} // aux default
    public MenuServiceDTO(MenuService menuService) {
        this.id = menuService.getId();
        this.idUser = menuService.getIdUser();
        this.nameService = menuService.getNameService();
        this.price = menuService.getPrice();
        this.people = menuService.getPeople();
    }
}
