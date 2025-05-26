package com.esthetic.servicesmicroservices.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;


// ESTA ENTITY SOLO ES DE APOYO PARA OBTENER DATOS DE LA LOCACION NO SE DEBE UTILIZAR PARA GUARDAR O MODIFICAR DATOS DE ESTA ENTITY
// SI EN EL user-microservices SE MODIFICA ALGUNO DE LOS CAMPOS QUE SE TIENE AQUI TAMBIEN SE DEBE MODIFICAR EN ESTA ENTITY
@Entity
@Table(name = "tbl_user_location")
@Data
@NoArgsConstructor
public class UserLocation {
    @Id
    private String id;
    @Column(name = "id_user")
    private String idUser;
    @OneToOne(mappedBy = "userLocation")
    @JsonManagedReference
    private ScheduleService scheduleService;
    private double latitude;
    private double longitude;
    @Column(name = "id_state")
    private Long idState;
    @Column(name = "id_municipality")
    private Long idMunicipality;
    @Column(name = "aux_state")
    private String auxState;
    @Column(name = "aux_municipality")
    private String auxMunicipality;
    private String reference;
}