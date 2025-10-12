package com.esthetic.servicesmicroservices.controller;

import com.esthetic.servicesmicroservices.dto.MenuServiceDTO;
import com.esthetic.servicesmicroservices.dto.ResponseDTO;
import com.esthetic.servicesmicroservices.services.MenuServiceServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/esthetic/auth/menu-service")
public class MenuServiceController {
    @Autowired
    private MenuServiceServices menuServiceServices;
    @GetMapping("/get-menu-service-by-user-id/{id-user}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO GetMenuServiceByUserId(@PathVariable(name = "id-user")String idUser) {
        try{
            return menuServiceServices._GetMenuServiceByIdUser(idUser);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return ResponseDTO.builder().error(true).message("Ocurrio un error intentelo mas tarde").build();
        }
    }
    @GetMapping("/get-menu-service-by-id")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO GetMenuServiceById(@RequestParam(name = "id-menu-service") Long idMenuService) {
        try {
            return menuServiceServices._GetMenuServiceById(idMenuService);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return ResponseDTO.builder().error(true).message("Ocurrio un error intentelo mas tarde").build();
        }
    }
    @PostMapping("/save-menu-service")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO SaveMenuService(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @RequestBody MenuServiceDTO menuServiceDTO) {
        try{
            return menuServiceServices._SaveMenuService(token, menuServiceDTO);
        }catch (Exception ex) {
            System.out.println(ex.getMessage());
            return ResponseDTO.builder().error(true).message("Ocurrio un error intentelo mas tarde").build();
        }
    }
    @PutMapping("/update-menu-service")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO UpdateMenuService(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @RequestBody MenuServiceDTO menuServiceDTO) {
        try{
            return  menuServiceServices._UpdateMenuService(token, menuServiceDTO);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return ResponseDTO.builder().error(true).message("Ocurrio un error intentelo mas tarde").build();
        }
    }
    @DeleteMapping("/delete-menu-service-by-id/{id-user}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO DeleteMenuServiceById(@PathVariable(name = "id-user") String idUser, @RequestParam(name = "id-menu-service") Long idMenuService) {
        try {
            return menuServiceServices._DeleteMenuService(idUser, idMenuService);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return ResponseDTO.builder().error(true).message("Ocurrio un error intentelo mas tarde").build();
        }
    }
}
