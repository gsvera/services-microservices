package com.esthetic.servicesmicroservices.controller;

import com.esthetic.servicesmicroservices.dto.MenuServiceDTO;
import com.esthetic.servicesmicroservices.dto.ResponseDTO;
import com.esthetic.servicesmicroservices.services.MenuServiceSerices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/esthetic/auth/menu-service")
public class MenuServiceController {
    @Autowired
    private MenuServiceSerices menuServiceSerices;
    @GetMapping("/get-menu-service-by-user-id/{id-user}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO GetMenuServiceByUserId(@PathVariable(name = "id-user")String idUser) {
        try{
            return menuServiceSerices._GetMenuServiceByIdUser(idUser);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return ResponseDTO.builder().error(true).message("Ocurrio un error intentelo mas tarde").build();
        }
    }
    @GetMapping("/get-menu-service-by-id")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO GetMenuServiceById(@RequestParam(name = "id-menu-service") Long idMenuService) {
        try {
            return menuServiceSerices._GetMenuServiceById(idMenuService);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return ResponseDTO.builder().error(true).message("Ocurrio un error intentelo mas tarde").build();
        }
    }
    @PostMapping("/save-menu-service")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO SaveMenuService(@RequestBody MenuServiceDTO menuServiceDTO) {
        try{
            return menuServiceSerices._SaveMenuService(menuServiceDTO);
        }catch (Exception ex) {
            System.out.println(ex.getMessage());
            return ResponseDTO.builder().error(true).message("Ocurrio un error intentelo mas tarde").build();
        }
    }
    @PutMapping("/update-menu-service")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO UpdateMenuService(@RequestBody MenuServiceDTO menuServiceDTO) {
        try{
            return  menuServiceSerices._UpdateMenuService(menuServiceDTO);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return ResponseDTO.builder().error(true).message("Ocurrio un error intentelo mas tarde").build();
        }
    }
    @DeleteMapping("/delete-menu-service-by-id/{id-user}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO DeleteMenuServiceById(@PathVariable(name = "id-user") String idUser, @RequestParam(name = "id-menu-service") Long idMenuService) {
        try {
            return menuServiceSerices._DeleteMenuService(idUser, idMenuService);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return ResponseDTO.builder().error(true).message("Ocurrio un error intentelo mas tarde").build();
        }
    }
}
