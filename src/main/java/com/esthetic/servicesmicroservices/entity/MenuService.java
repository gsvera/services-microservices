package com.esthetic.servicesmicroservices.entity;

import com.esthetic.servicesmicroservices.dto.MenuServiceDTO;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "tbl_menu_service")
public class MenuService {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "id_user")
    private String idUser;
    @Column(name = "name_service")
    private String nameService;
    private Double price;
    private Integer people;
    public MenuService(){} // Lo requiere jpa como constructor default
    public MenuService(MenuServiceDTO menuServiceDTO) {
        this.idUser = menuServiceDTO.idUser;
        this.nameService = menuServiceDTO.nameService;
        this.price = menuServiceDTO.price;
        this.people = menuServiceDTO.people;
    }
}
