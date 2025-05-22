package com.esthetic.servicesmicroservices.controller;

import com.esthetic.servicesmicroservices.dto.ResponseDTO;
import com.esthetic.servicesmicroservices.services.CalendarServices;
import com.esthetic.servicesmicroservices.services.MenuServiceServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/esthetic/public/calendar")
public class CalendarPublicController {
    @Autowired
    private CalendarServices calendarServices;
    @Autowired
    private MenuServiceServices menuServiceServices;
    @GetMapping("/get-time-by-provider/{id-provider}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO GetTimeByProvider(@PathVariable(name = "id-provider") String idProvider, @RequestParam String day, @RequestParam String date) {
        try{
            LocalDate localDate = LocalDate.parse(date);
            LocalDateTime localDateTime = localDate.atStartOfDay();
            return calendarServices._GetTimeByProvider(idProvider, day, localDateTime);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return ResponseDTO.builder().error(true).message("Ocurrio un error intentelo mas tarde").build();
        }
    }
    @GetMapping("/get-services-by-provider/{id-provider}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO GetServicesByProvider(@PathVariable(name = "id-provider")String idProvider) {
        try{
            return menuServiceServices._GetMenuServiceByIdUser(idProvider);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return ResponseDTO.builder().error(true).message("Ocurrio un error intentelo mas tarde").build();
        }
    }
}
