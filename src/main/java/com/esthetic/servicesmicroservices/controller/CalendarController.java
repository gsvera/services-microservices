package com.esthetic.servicesmicroservices.controller;

import com.esthetic.servicesmicroservices.dto.ResponseDTO;
import com.esthetic.servicesmicroservices.dto.UserServicesCalendarDTO;
import com.esthetic.servicesmicroservices.dto.UserServicesCalendarExceptionDTO;
import com.esthetic.servicesmicroservices.services.CalendarServices;
import com.esthetic.servicesmicroservices.services.MenuServiceServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/esthetic/calendar")
public class CalendarController {
    @Autowired
    private CalendarServices calendarServices;
    @Autowired
    private MenuServiceServices menuServiceServices;
    @GetMapping("/get-calendar-by-user/{id-user}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO GetCalendarByUser(@PathVariable(name = "id-user") String idUser) {
        try{
            return calendarServices._GetCalendarByUser(idUser);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return ResponseDTO.builder().error(true).message("Ocurrio un error intentelo mas tarde").build();
        }
    }
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
    @PostMapping("/save-calendar")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO SaveCalendar(@RequestBody List<UserServicesCalendarDTO> userServicesCalendarDTOList) {
        try{
            return calendarServices._SaveCalendar(userServicesCalendarDTOList);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return ResponseDTO.builder().error(true).message("Ocurrio un error intentelo mas tarde").build();
        }
    }
    @GetMapping("/get-calendar-exception-by-user/{id-user}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO GetExceptionCalendarByUser(@PathVariable(name = "id-user") String idUser, @RequestParam("date-tostring")LocalDateTime dateToString) {
        try{
            return calendarServices._GetExceptionCalendarByUser(idUser, dateToString);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return ResponseDTO.builder().error(true).message(ex.getMessage()).build();
        }
    }
    @PostMapping("/save-calendar-exception")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO SaveCalendarException(@RequestBody UserServicesCalendarExceptionDTO userServicesCalendarExceptionDTO) {
        try{
            return calendarServices._SaveCelendarException(userServicesCalendarExceptionDTO);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return ResponseDTO.builder().error(true).message("Ocurrio un error intentelo mas tarde").build();
        }
    }
    @PutMapping("/update-calendar-exception")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO UpdateCalendarException(@RequestBody UserServicesCalendarExceptionDTO userServicesCalendarExceptionDTO) {
        try{
            return calendarServices._UpdateCalendarException(userServicesCalendarExceptionDTO);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return ResponseDTO.builder().error(true).message("Ocurrio un error intentelo mas tarde").build();
        }
    }
    @DeleteMapping("/delete-calendar-exception")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO DeleteCalendarException(@RequestParam(name = "id-exception")Long idException) {
        try{
            return calendarServices._DeleteCalendarException(idException);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return ResponseDTO.builder().error(true).message("Ocurrio un error intentelo mas tarde").build();
        }
    }
}
