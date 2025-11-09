package com.esthetic.servicesmicroservices.services;

import com.esthetic.servicesmicroservices.dto.NotificationDTO;
import com.esthetic.servicesmicroservices.dto.ResponseDTO;
import com.esthetic.servicesmicroservices.dto.ScheduleServiceDTO;
import com.esthetic.servicesmicroservices.entity.*;
import com.esthetic.servicesmicroservices.repository.CatalogStatusScheduleServiceRepository;
import com.esthetic.servicesmicroservices.repository.ScheduleServiceRepository;
import com.esthetic.servicesmicroservices.repository.TempClientRepository;
import com.esthetic.servicesmicroservices.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleServiceServices {
    private final ScheduleServiceRepository scheduleServiceRepository;
    private final UserRepository userRepository;
    private final CatalogStatusScheduleServiceRepository catalogStatusScheduleServiceRepository;
    private final NotificationService notificationService;
    private final PushNotificationServices pushNotificationServices;
    private final ProviderRatingsServices providerRatingsServices;
    private final TempClientRepository tempClientRepository;
    private final UserService userService;
    public ResponseDTO _MakeScheduleServicec(ScheduleServiceDTO scheduleServiceDTO) {
        Optional<User> user = userRepository.findById(scheduleServiceDTO.idClientAux);
        if(user.isPresent()) {
            Optional<ScheduleService> existScheduleService = scheduleServiceRepository.findExistSchedule(scheduleServiceDTO.idClientAux, scheduleServiceDTO.scheduleDate, scheduleServiceDTO.startTime, scheduleServiceDTO.endTime);
            if(existScheduleService.isPresent()) {
                return ResponseDTO.builder().error(true).message("Ya cuenta con una cita en el mismo rango de horario").items(existScheduleService.get()).build();
            }
            scheduleServiceDTO.createdAt = Timestamp.from(Instant.now());
            ScheduleService scheduleService = new ScheduleService(scheduleServiceDTO, user.get());
            scheduleServiceRepository.save(scheduleService);

            NotificationDTO notificationDTO = new NotificationDTO("new-schedule", "Nueva cita", "Se ha generado nueva cita para el día: "+ scheduleServiceDTO.scheduleDate.toLocalDate(), scheduleService.getId().toString());
            this._SendNotifications(scheduleServiceDTO.idProviderAux, notificationDTO);
            return ResponseDTO.builder().message("Reservación generada con éxito").build();
        }
        return ResponseDTO.builder().error(true).message("Cliente no encontrado").build();
    }
    public ResponseDTO _MakeOurScheduleService(String token, ScheduleServiceDTO scheduleServiceDTO) {

        ResponseDTO responseDTO = userService._ValidIsActiveProvider(token, scheduleServiceDTO.idProviderAux);

        if(responseDTO.error){
            return responseDTO;
        }

        scheduleServiceDTO.createdAt = Timestamp.from(Instant.now());
        ScheduleService scheduleService = new ScheduleService(scheduleServiceDTO);
        scheduleServiceRepository.save(scheduleService);

        if(scheduleServiceDTO.saveTempClient) {
            Optional<TempClient> tempClient = tempClientRepository.findExistTempClient(scheduleServiceDTO.tempPhoneClient, scheduleServiceDTO.idProviderAux);
            if(!tempClient.isPresent()){
                TempClient newTempClient = new TempClient(scheduleServiceDTO.idProviderAux, scheduleServiceDTO.tempNameClient, scheduleServiceDTO.tempLadaClient, scheduleServiceDTO.tempPhoneClient);
                tempClientRepository.save(newTempClient);
            }
        }
        return ResponseDTO.builder().message("Reservación generada con éxito").build();
    }
    public ResponseDTO _MakePublicScheduleService(ScheduleServiceDTO scheduleServiceDTO) {
        scheduleServiceDTO.createdAt = Timestamp.from(Instant.now());
        ScheduleService scheduleService = new ScheduleService(scheduleServiceDTO);
        scheduleServiceRepository.save(scheduleService);

        NotificationDTO notificationDTO = new NotificationDTO("new-schedule", "Nueva cita", "Se ha generado nueva cita para el día: "+ scheduleServiceDTO.scheduleDate.toLocalDate(), scheduleService.getId().toString());
        this._SendNotifications(scheduleServiceDTO.idProviderAux, notificationDTO);

        return ResponseDTO.builder().message("Reservación generada con éxito").build();
    }
    public void _SendNotifications(String idUser, NotificationDTO notificationDTO) {
        Optional<User> user = userRepository.findById(idUser);
        if(user.isPresent() && user.get().getTokenNotification() != null) {
            System.out.println(user.get().getTokenNotification());
            notificationService.sendPrivateNotification(idUser, notificationDTO);
            pushNotificationServices.sendPushNotification(user.get().getTokenNotification(),notificationDTO.title, notificationDTO.message );
        }
    }
    public ResponseDTO _FindSchedulesByClient(String idClient, LocalDateTime date) {
        List<ScheduleService> lisScheduleService = scheduleServiceRepository.findScheduleByClient(idClient, date);
        return ResponseDTO.builder().items(lisScheduleService.stream().map(item -> new ScheduleServiceDTO(item)).collect(Collectors.toList())).build();
    }
    public ResponseDTO _FindAllByProvider(String idProvider, LocalDateTime date, Integer statusSchedule) {
        List<ScheduleServiceDTO> listSchedule = null;
        if(statusSchedule != null) {
            listSchedule = scheduleServiceRepository.findByIdProviderAndStatusService(idProvider, date, statusSchedule)
                    .stream()
                    .map(item -> new ScheduleServiceDTO(item))
                    .collect(Collectors.toList());
        } else {
            listSchedule = scheduleServiceRepository.findByIdProvider(idProvider, date)
                    .stream()
                    .map(item -> new ScheduleServiceDTO(item))
                    .collect(Collectors.toList());
        }
        return ResponseDTO.builder().items(listSchedule).build();
    }
    public ResponseDTO _ChangeStatusSchedule(Long idSchedule, String textComments, int statusSchedule) {
        Optional<ScheduleService> scheduleService = scheduleServiceRepository.findById(idSchedule);
        Optional<CatalogStatusScheduleService> catalogStatusScheduleService = catalogStatusScheduleServiceRepository.findByStatusValue(statusSchedule);
        if(scheduleService.isPresent()) {
            scheduleService.orElseThrow().setStatusService(statusSchedule);
            scheduleService.orElseThrow().setComments(textComments);
            scheduleServiceRepository.save(scheduleService.get());

            String message = "Cita para "+scheduleService.get().getNameService()+", el día " + scheduleService.get().getScheduleDate().toLocalDate() + ", se ha " + catalogStatusScheduleService.get().getStatusName();

            NotificationDTO notificationDTO = new NotificationDTO("update-schedule", "Actualización de cita", message, scheduleService.get().getId().toString());
            this._SendNotifications(scheduleService.get().getIdProvider().getId(), notificationDTO);
            if(scheduleService.get().getIdClient() != null) {
                this._SendNotifications(scheduleService.get().getIdClient().getId(), notificationDTO);
                if(catalogStatusScheduleService.get().getStatusDone()) {
                    providerRatingsServices._MakeRatingsByService(
                            new ProviderRatings(
                                    scheduleService.get().getIdClient().getId(),
                                    scheduleService.get().getIdProvider().getId(),
                                    scheduleService.get().getId()
                            )
                    );
                }
            }

            return ResponseDTO.builder().message("Se actualizo el estatus con éxito").build();
        }
        return ResponseDTO.builder().error(true).message("No se encontro el registro").build();
    }
}
