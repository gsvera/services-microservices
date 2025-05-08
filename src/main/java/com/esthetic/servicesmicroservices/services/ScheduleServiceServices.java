package com.esthetic.servicesmicroservices.services;

import com.esthetic.servicesmicroservices.dto.ResponseDTO;
import com.esthetic.servicesmicroservices.dto.ScheduleServiceDTO;
import com.esthetic.servicesmicroservices.entity.ScheduleService;
import com.esthetic.servicesmicroservices.repository.ScheduleServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScheduleServiceServices {
    private final ScheduleServiceRepository scheduleServiceRepository;
    public ResponseDTO _MakeScheduleServicec(ScheduleServiceDTO scheduleServiceDTO) {
        scheduleServiceRepository.save(new ScheduleService(scheduleServiceDTO));
        return ResponseDTO.builder().message("Reservación generada con éxito").build();
    }
}
