package com.esthetic.servicesmicroservices.services;

import com.esthetic.servicesmicroservices.dto.ResponseDTO;
import com.esthetic.servicesmicroservices.dto.UserServicesCalendarDTO;
import com.esthetic.servicesmicroservices.dto.UserServicesCalendarExceptionDTO;
import com.esthetic.servicesmicroservices.entity.UserServicesCalendar;
import com.esthetic.servicesmicroservices.entity.UserServicesCalendarException;
import com.esthetic.servicesmicroservices.repository.UserServicesCalendarExceptionRepository;
import com.esthetic.servicesmicroservices.repository.UserServicesCalendarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CalendarServices {
    private final UserServicesCalendarRepository userServicesCalendarRepository;
    private final UserServicesCalendarExceptionRepository userServicesCalendarExceptionRepository;
    public ResponseDTO _GetCalendarByUser(String idUser) {
        return ResponseDTO.builder().items(
                userServicesCalendarRepository.getCalendarByUser(idUser).stream().map(item -> new UserServicesCalendarDTO(item)).collect(Collectors.toList())
        ).build();
    }
    public ResponseDTO _GetTimeByProvider(String idUser, String day) {
        return ResponseDTO.builder().items(
                new UserServicesCalendarDTO(
                        userServicesCalendarRepository.getTimeByProvider(idUser, day).get()
                )).build();
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
