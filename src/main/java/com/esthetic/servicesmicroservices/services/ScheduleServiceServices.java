package com.esthetic.servicesmicroservices.services;

import com.esthetic.servicesmicroservices.dto.ResponseDTO;
import com.esthetic.servicesmicroservices.dto.ScheduleServiceDTO;
import com.esthetic.servicesmicroservices.entity.ScheduleService;
import com.esthetic.servicesmicroservices.entity.User;
import com.esthetic.servicesmicroservices.repository.ScheduleServiceRepository;
import com.esthetic.servicesmicroservices.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
            scheduleServiceRepository.save(new ScheduleService(scheduleServiceDTO,user.get()));
            return ResponseDTO.builder().message("Reservación generada con éxito").build();
        }
        return ResponseDTO.builder().error(true).message("Cliente no encontrado").build();
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
    public ResponseDTO _AcceptScheduleByProvider(Long idSchedule) {
        Optional<ScheduleService> scheduleService = scheduleServiceRepository.findById(idSchedule);
        if(scheduleService.isPresent()) {
            scheduleService.orElseThrow().setStatusService(1); // SE USA 1 PARA CONFIRMAR EL SERVICIO
            scheduleServiceRepository.save(scheduleService.get());
            return ResponseDTO.builder().message("Se acepto la reservación con éxito").build();
        }
        return ResponseDTO.builder().error(true).message("No se encontro el registro").build();
    }
    public ResponseDTO _RejectScheduleByProvider(Long idSchedule, String textReject) {
        Optional<ScheduleService> scheduleService = scheduleServiceRepository.findById(idSchedule);
        if(scheduleService.isPresent()) {
            scheduleService.orElseThrow().setStatusService(-1); // SE USA -1 PARA MARCAR COMO RECHAZADO
            scheduleService.orElseThrow().setCommentReject(textReject);
            scheduleServiceRepository.save(scheduleService.get());
            return ResponseDTO.builder().message("Reservación rechazada con éxito").build();
        } else {
            return ResponseDTO.builder().error(true).message("No se encontro el registro").build();
        }
    }
}
