package com.esthetic.servicesmicroservices.controller;

import com.esthetic.servicesmicroservices.dto.ResponseDTO;
import com.esthetic.servicesmicroservices.services.DeleteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/esthetic/delete-user-services")
public class DeleteUserController {
    @Autowired
    private DeleteService deleteService;
    @DeleteMapping("/delete-services-account/{id-user}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO DeleteServicesAccount(@PathVariable(name = "id-user") String idUser) {
        try{
            return deleteService._DeleteAllConfigServices(idUser);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return ResponseDTO.builder().error(true).message("Ocurrio un error intentelo mas tarde").build();
        }
    }
}
