package com.esthetic.servicesmicroservices.controller;

import com.esthetic.servicesmicroservices.dto.ResponseDTO;
import com.esthetic.servicesmicroservices.dto.ScheduleServiceDTO;
import com.esthetic.servicesmicroservices.services.ScheduleServiceServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
