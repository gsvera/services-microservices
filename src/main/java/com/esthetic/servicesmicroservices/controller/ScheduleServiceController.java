package com.esthetic.servicesmicroservices.controller;

import com.esthetic.servicesmicroservices.dto.NotificationDTO;
import com.esthetic.servicesmicroservices.dto.ProviderRatingsDTO;
import com.esthetic.servicesmicroservices.dto.ResponseDTO;
import com.esthetic.servicesmicroservices.dto.ScheduleServiceDTO;
import com.esthetic.servicesmicroservices.services.ProviderRatingsServices;
import com.esthetic.servicesmicroservices.services.PushNotificationServices;
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
    @Autowired
    private ProviderRatingsServices providerRatingsServices;
    @Autowired
    private PushNotificationServices pushNotificationServices;
//    @PostMapping("/push-notification/{id-user}")
//    public ResponseDTO PushNotification(@PathVariable(name = "id-user") String idUser) {
//        try{
//            scheduleServiceServices._SendNotifications(idUser, new NotificationDTO("typeEvent", "title", "message", "dataid"));
//            return ResponseDTO.builder().build();
//        } catch (Exception ex) {
//            System.out.println(ex.getMessage());
//            return ResponseDTO.builder().error(true).message("Ocurrio un error intentelo mas tarde").build();
//        }
//    }
    @PostMapping("/make-schedule-service")
    public ResponseDTO MakeScheduleService(@RequestBody ScheduleServiceDTO scheduleServiceDTO) {
        try {
            return scheduleServiceServices._MakeScheduleServicec(scheduleServiceDTO);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return ResponseDTO.builder().error(true).message("Ocurrio un error intentelo mas tarde").build();
        }
    }
    @GetMapping("/find-schedules-by-client/{id-client}")
    public ResponseDTO FindSchedulesByClient(@PathVariable(name = "id-client") String idClient, @RequestParam String date) {
        try{
            LocalDate localDate = LocalDate.parse(date);
            LocalDateTime localDateTime = localDate.atStartOfDay();
            return scheduleServiceServices._FindSchedulesByClient(idClient, localDateTime);
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
    @PatchMapping("/change-status-schedule")
    public ResponseDTO ChangeStatueSchedule(@RequestParam("id-schedule") Long idSchedule, @RequestParam("status-schedule") int statusSchedule, @RequestParam(name = "text-comments", required = false) String textComments) {
        try{
            return scheduleServiceServices._ChangeStatusSchedule(idSchedule,textComments, statusSchedule);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return ResponseDTO.builder().error(true).message("Ocurrio un error intentelo mas tarde").build();
        }
    }
    @GetMapping("/get-pending-rating-by-user/{id-user}")
    public ResponseDTO GetPendingRatingByUser(@PathVariable(name = "id-user") String idUser) {
        try{
            return providerRatingsServices._GetPendingRatingByUser(idUser);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return ResponseDTO.builder().error(true).message("Ocurrio un error intentelo mas tarde").build();
        }
    }
    @PostMapping("/update-rating-by-service")
    public ResponseDTO MakeRatingByService(@RequestBody ProviderRatingsDTO providerRatingsDTO) {
        try {
            return providerRatingsServices._UpdateRatingByService(providerRatingsDTO);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return ResponseDTO.builder().error(true).message("Ocurrio un error intentelo mas tarde").build();
        }
    }
    @DeleteMapping("/delete-rating-by-service")
    public ResponseDTO DeleteRatingByService(@RequestParam(name = "id-rating") Long id) {
        try{
            providerRatingsServices._DeleteRatingByService(id);
            return ResponseDTO.builder().message("ok").build();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return ResponseDTO.builder().error(true).message("Ocurrio un error intentelo mas tarde").build();
        }
    }
    @GetMapping("/get-ratings-by-provider/{id-provider}")
    public ResponseDTO GetRatingsByProvider(@PathVariable(name = "id-provider") String idProvider) {
        try{
            return providerRatingsServices._GetRatingsByProvider(idProvider);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return ResponseDTO.builder().error(true).message("Ocurrio un error intentelo mas tarde").build();
        }
    }
}
