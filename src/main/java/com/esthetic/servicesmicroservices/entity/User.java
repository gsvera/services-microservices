package com.esthetic.servicesmicroservices.entity;

import com.esthetic.servicesmicroservices.dto.UserDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// ESTA ENTITY SOLO ES DE APOYO PARA OBTENER DATOS DEL USUARIO NO SE DEBE UTILIZAR PARA GUARDAR O MODIFICAR DATOS DE ESTA ENTITY
// SI EN EL user-microservices SE MODIFICA ALGUNO DE LOS CAMPOS QUE SE TIENE AQUI TAMBIEN SE DEBE MODIFICAR EN ESTA ENTITY
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_user")
public class User {
    @Id
    private String id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    private String email;
    private String lada;
    private String phone;
    @Column(name = "token_notification")
    private String tokenNotification;
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonBackReference
    private InfoCompany userInfoCompany;
    public User (UserDTO userDTO) {
        this.id = userDTO.id;
    }
    public User(String idAux) {
        this.id = idAux;
    }
}
