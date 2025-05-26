package com.esthetic.servicesmicroservices.services;

import com.esthetic.servicesmicroservices.dto.ResponseDTO;
import com.esthetic.servicesmicroservices.dto.ScheduleServiceDTO;
import com.esthetic.servicesmicroservices.entity.ScheduleService;
import com.esthetic.servicesmicroservices.entity.User;
import com.esthetic.servicesmicroservices.repository.ScheduleServiceRepository;
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
    public ResponseDTO _MakeScheduleServicec(ScheduleServiceDTO scheduleServiceDTO) {
        Optional<User> user = userRepository.findById(scheduleServiceDTO.idClientAux);
        if(user.isPresent()) {
            scheduleServiceDTO.createdAt = Timestamp.from(Instant.now());
            scheduleServiceRepository.save(new ScheduleService(scheduleServiceDTO,user.get()));
            return ResponseDTO.builder().message("Reservación generada con éxito").build();
        }
        return ResponseDTO.builder().error(true).message("Cliente no encontrado").build();
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
        if(scheduleService.isPresent()) {
            scheduleService.orElseThrow().setStatusService(statusSchedule);
            scheduleService.orElseThrow().setComments(textComments);
            scheduleServiceRepository.save(scheduleService.get());
            return ResponseDTO.builder().message("Se actualizo el estatus con éxito").build();
        }
        return ResponseDTO.builder().error(true).message("No se encontro el registro").build();
    }
}
