package com.esthetic.servicesmicroservices.services;

import com.esthetic.servicesmicroservices.dto.ResponseDTO;
import com.esthetic.servicesmicroservices.dto.TimeSlotDTO;
import com.esthetic.servicesmicroservices.dto.UserServicesCalendarDTO;
import com.esthetic.servicesmicroservices.dto.UserServicesCalendarExceptionDTO;
import com.esthetic.servicesmicroservices.entity.ScheduleService;
import com.esthetic.servicesmicroservices.entity.UserServicesCalendar;
import com.esthetic.servicesmicroservices.entity.UserServicesCalendarException;
import com.esthetic.servicesmicroservices.repository.ScheduleServiceRepository;
import com.esthetic.servicesmicroservices.repository.UserServicesCalendarExceptionRepository;
import com.esthetic.servicesmicroservices.repository.UserServicesCalendarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CalendarServices {
    private final UserServicesCalendarRepository userServicesCalendarRepository;
    private final UserServicesCalendarExceptionRepository userServicesCalendarExceptionRepository;
    private final ScheduleServiceRepository scheduleServiceRepository;
    public ResponseDTO _GetCalendarByUser(String idUser) {
        return ResponseDTO.builder().items(
                userServicesCalendarRepository.getCalendarByUser(idUser).stream().map(item -> new UserServicesCalendarDTO(item)).collect(Collectors.toList())
        ).build();
    }
    public ResponseDTO _GetTimeByProvider(String idUser, String day, LocalDateTime date) {
        List<TimeSlotDTO> listTimeSlot = new ArrayList<>();

        Optional<UserServicesCalendar> userServicesCalendar = userServicesCalendarRepository.getTimeByProvider(idUser, day);
        Optional<UserServicesCalendarException> userServicesCalendarException = userServicesCalendarExceptionRepository.findByIdUserAndDateString(idUser, date);
        List<ScheduleService> listScheduleService = scheduleServiceRepository.findScheduleByProvider(idUser, date);

        if(userServicesCalendarException.isPresent()) {
            listTimeSlot = this._MakeAvailableSchedule(userServicesCalendarException.get().getStartTime(), userServicesCalendarException.get().getEndTime(), userServicesCalendarException.get().getDuration(), listScheduleService);
        } else if(userServicesCalendar.isPresent()) {
            listTimeSlot = this._MakeAvailableSchedule(userServicesCalendar.get().getStartTime(), userServicesCalendar.get().getEndTime(), userServicesCalendar.get().getDuration(), listScheduleService);
        }

        return ResponseDTO.builder().items(listTimeSlot).build();
    }
    private List<TimeSlotDTO> _MakeAvailableSchedule(String startTime, String endTime, int minDuration, List<ScheduleService> listScheduleService) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        List<TimeSlotDTO> listTimeSlot = new ArrayList<>();
        LocalTime start = LocalTime.parse(startTime, formatter);
        LocalTime end = LocalTime.parse(endTime, formatter);
        LocalTime current = start;

        while (current.isBefore(end)) {
            LocalTime next = current.plusMinutes(minDuration);

            // Ajusta el slot final si se pasa del endTime
            if (next.isAfter(end)) {
                next = end;
            }

            String startStr = current.format(formatter);
            String endStr = next.format(formatter);

            boolean isReserved = listScheduleService.stream()
                                    .anyMatch(item -> item.getStartTime().equals(startStr)
                                            && item.getEndTime().equals(endStr));

            if(!isReserved) {
                listTimeSlot.add(new TimeSlotDTO(startStr, endStr));
            }

            current = next;
        }
        return listTimeSlot;
    }
    public ResponseDTO _GetExceptionCalendarByUser(String idUser, LocalDateTime dateTime) {
        Optional<UserServicesCalendarException> userServicesCalendarExceptionOption = userServicesCalendarExceptionRepository.findByIdUserAndDateString(idUser, dateTime);
        return ResponseDTO.builder().items(
                new UserServicesCalendarExceptionDTO(userServicesCalendarExceptionOption.get())
        ).build();
    }
    public ResponseDTO _SaveCalendar(List<UserServicesCalendarDTO> userServicesCalendarDTOList) {
        userServicesCalendarRepository.deleteAllCalendarByUser(userServicesCalendarDTOList.get(0).idUser);
        userServicesCalendarRepository.saveAll(userServicesCalendarDTOList.stream().map(item -> new UserServicesCalendar(item)).collect(Collectors.toList()));
        return ResponseDTO.builder().message("Registros guardado con éxito").build();
    }
    public ResponseDTO _SaveCelendarException(UserServicesCalendarExceptionDTO userServicesCalendarExceptionDTO) {
        userServicesCalendarExceptionRepository.save(new UserServicesCalendarException(userServicesCalendarExceptionDTO));
        return ResponseDTO.builder().message("Registro guardado con éxito").build();
    }
    public ResponseDTO _UpdateCalendarException(UserServicesCalendarExceptionDTO userServicesCalendarExceptionDTO) {
        Optional<UserServicesCalendarException> userServicesCalendarException = userServicesCalendarExceptionRepository.findById(userServicesCalendarExceptionDTO.id);
        if(userServicesCalendarException.isPresent()) {
            userServicesCalendarException.orElseThrow().setComments(userServicesCalendarExceptionDTO.comments);
            userServicesCalendarException.orElseThrow().setStartTime(userServicesCalendarExceptionDTO.startTime);
            userServicesCalendarException.orElseThrow().setEndTime(userServicesCalendarExceptionDTO.endTime);
            userServicesCalendarException.orElseThrow().setIsActive(userServicesCalendarExceptionDTO.isActive);
            userServicesCalendarException.orElseThrow().setDuration(userServicesCalendarExceptionDTO.duration);
            userServicesCalendarException.orElseThrow().setMaxReservations(userServicesCalendarExceptionDTO.maxReservations);
            userServicesCalendarExceptionRepository.save(userServicesCalendarException.get());
            return ResponseDTO.builder().message("Registro actualizado con éxito").build();
        }
        return ResponseDTO.builder().error(true).message("Registro no encontrado").build();
    }
    public ResponseDTO _DeleteCalendarException(Long idException) {
        userServicesCalendarExceptionRepository.deleteById(idException);
        return ResponseDTO.builder().message("Registro eliminado con éxito").build();
    }
}
