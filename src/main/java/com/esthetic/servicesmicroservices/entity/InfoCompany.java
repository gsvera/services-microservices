package com.esthetic.servicesmicroservices.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

// ESTA ENTITY SOLO ES DE APOYO PARA OBTENER DATOS DE INFOCOMPANY NO SE DEBE UTILIZAR PARA GUARDAR O MODIFICAR DATOS DE ESTA ENTITY
// SI EN EL user-microservices SE MODIFICA ALGUNO DE LOS CAMPOS QUE SE TIENE AQUI TAMBIEN SE DEBE MODIFICAR EN ESTA ENTITY
@Entity
@Data
@Table(name = "tbl_info_company")
public class InfoCompany {
    @Id
    private Long id;
    @OneToOne
    @JoinColumn(name = "id_user", insertable = true, updatable = true, nullable = false)
    @JsonBackReference
    private User user;
    @Column(name = "company_name")
    private String companyName;
    @Column(name = "company_picture")
    private String companyPicture;
}
