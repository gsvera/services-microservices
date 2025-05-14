package com.esthetic.servicesmicroservices.controller;

import com.esthetic.servicesmicroservices.dto.ResponseDTO;
import com.esthetic.servicesmicroservices.dto.ScheduleServiceDTO;
import com.esthetic.servicesmicroservices.services.ScheduleServiceServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/esthetic/schedule-service")
public class ScheduleServiceController {
    @Autowired
    private ScheduleServiceServices scheduleServiceServices;
    @PostMapping("/make-schedule-service")
    public ResponseDTO MakeScheduleService(@RequestBody ScheduleServiceDTO scheduleServiceDTO) {
        try {
            return scheduleServiceServices._MakeScheduleServicec(scheduleServiceDTO);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return ResponseDTO.builder().error(true).message("Ocurrio un error intentelo mas tarde").build();
        }
    }
    @GetMapping("/find-all-by-provider/{id-provider}")
    public ResponseDTO FindAllByProvider(@PathVariable(name = "id-provider") String idProvider, @RequestParam("date") String date, @RequestParam(name = "status-schedule", required = false) Integer statusSchedule) {
        try {
            LocalDate localDate = LocalDate.parse(date);
            LocalDateTime dateParse = localDate.atStartOfDay();
            return scheduleServiceServices._FindAllByProvider(idProvider, dateParse, statusSchedule);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return ResponseDTO.builder().error(true).message("Ocurrio un error intentelo mas tarde").build();
        }
    }
    @PatchMapping("/accept-schedule-by-provider")
    public ResponseDTO AcceptScheduleByProvider(@RequestParam("id-schedule") Long idSchedule) {
     try{
         return scheduleServiceServices._AcceptScheduleByProvider(idSchedule);
     } catch (Exception ex) {
         System.out.println(ex.getMessage());
         return ResponseDTO.builder().error(true).message("Ocurrio un error intentelo mas tarde").build();
     }
    }
    @PatchMapping("/reject-schedule-by-provider")
    public ResponseDTO RejectScheduleByProvider(@RequestParam("id-schedule") Long idSchedule, @RequestParam("text-reject") String textReject) {
        try{
            return scheduleServiceServices._RejectScheduleByProvider(idSchedule, textReject);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return ResponseDTO.builder().error(true).message("Ocurrio un error intentelo mas tarde").build();
        }
    }
}
