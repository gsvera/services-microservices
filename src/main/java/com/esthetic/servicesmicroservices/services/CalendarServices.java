package com.esthetic.servicesmicroservices.services;

import com.esthetic.servicesmicroservices.config.EnvConfig;
import com.esthetic.servicesmicroservices.dto.*;
import com.esthetic.servicesmicroservices.entity.ScheduleService;
import com.esthetic.servicesmicroservices.entity.ShareCalendar;
import com.esthetic.servicesmicroservices.entity.UserServicesCalendar;
import com.esthetic.servicesmicroservices.entity.UserServicesCalendarException;
import com.esthetic.servicesmicroservices.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CalendarServices {
    private final UserServicesCalendarRepository userServicesCalendarRepository;
    private final UserServicesCalendarExceptionRepository userServicesCalendarExceptionRepository;
    private final ScheduleServiceRepository scheduleServiceRepository;
    private final UserService userService;
    private final ShareCalendarRepository shareCalendarRepository;
    private final EnvConfig envConfig;
    private final UserRepository userRepository;
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
    public ResponseDTO _MakeUrlToShareCalendar(String token, String idProvider){
        ResponseDTO responseDTO = userService._ValidIsActiveProvider(token, idProvider);

        if(responseDTO.error){
            return responseDTO;
        }

        String tokenCalendar = "";

        Optional<ShareCalendar> shareCalendar = shareCalendarRepository.findByIdProvider(idProvider);

        if(shareCalendar.isPresent()) {
            if (_IsActiveToken(shareCalendar.get().getCreatedAt())) {
                tokenCalendar = shareCalendar.get().getToken();
            } else  {
                shareCalendarRepository.delete(shareCalendar.get());
            }
        }

        if(tokenCalendar.equals("")){
            tokenCalendar = this._MakeTokenCalendar(idProvider);
        }

        String url = envConfig.getHostname() + "/calendar/"+ tokenCalendar;
        return ResponseDTO.builder().items(url).build();
    }
    public Boolean _IsActiveToken(Instant createdAt) {
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        long millisecondsIn24Hours = 1000 * 60 * 60 * 24;

        return currentTime.getTime() - createdAt.toEpochMilli() <= millisecondsIn24Hours;
    }
    public String _MakeTokenCalendar(String idProvider) {
        UUID uuid = UUID.randomUUID();
        String tokenCalendar = uuid.toString();
        ShareCalendar newShareCalendar = new ShareCalendar(idProvider, tokenCalendar, Instant.now());
        shareCalendarRepository.save(newShareCalendar);

        return tokenCalendar;
    }
    public ShareCalendarDTO _IsValidToken(String token) {
        Optional<ShareCalendar> shareCalendar = shareCalendarRepository.findByToken(token);
        if(this._IsActiveToken(shareCalendar.get().getCreatedAt())) {
            return new ShareCalendarDTO(shareCalendar.get());
        }
        return null;
    }
    public ResponseDTO _GetInfoProviderByUrlToken(String shareTokenCalendar) {
        ShareCalendarDTO isActiveToken = this._IsValidToken(shareTokenCalendar);
        if(isActiveToken != null) {
            List<Object[]> result = userRepository.GetPublicInfoProvider(shareTokenCalendar);
            if(!result.isEmpty()) {
                Object[] userPublicInfo = result.get(0);
                PublicInfoProviderDTO publicInfoProviderDTO = new PublicInfoProviderDTO();
                publicInfoProviderDTO.id = (String) userPublicInfo[0];
                publicInfoProviderDTO.companyName = (String) userPublicInfo[1];
                publicInfoProviderDTO.companyPictureUrl = (String) userPublicInfo[2];
                publicInfoProviderDTO.latitude = (double) userPublicInfo[3];
                publicInfoProviderDTO.longitude = (double) userPublicInfo[4];
                publicInfoProviderDTO.auxState = (String) userPublicInfo[5];
                publicInfoProviderDTO.auxMunicipality = (String) userPublicInfo[6];
                publicInfoProviderDTO.reference = (String) userPublicInfo[7];

                return ResponseDTO.builder().items(publicInfoProviderDTO).build();
            }
        }
        return ResponseDTO.builder().error(true).build();
    }
}
