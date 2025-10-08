package com.esthetic.servicesmicroservices.services;

import com.esthetic.servicesmicroservices.dto.ResponseDTO;
import com.esthetic.servicesmicroservices.repository.MenuServiceRepository;
import com.esthetic.servicesmicroservices.repository.ProviderRatingsRepository;
import com.esthetic.servicesmicroservices.repository.UserServicesCalendarExceptionRepository;
import com.esthetic.servicesmicroservices.repository.UserServicesCalendarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteService {
    private final UserServicesCalendarRepository userServicesCalendarRepository;
    private final UserServicesCalendarExceptionRepository userServicesCalendarExceptionRepository;
    private final MenuServiceRepository menuServiceRepository;
    private final ProviderRatingsRepository providerRatingsRepository;
    private final TempClientServices tempClientServices;
    public ResponseDTO _DeleteAllConfigServices(String idUser) {
        userServicesCalendarRepository.deleteAllCalendarByUser(idUser);
        userServicesCalendarExceptionRepository.deleteAllByUser(idUser);
        menuServiceRepository.deleteAllByUser(idUser);
        providerRatingsRepository.deleteAllByUser(idUser);
        tempClientServices._DeleteAllTempContactByProvider(idUser);
        return ResponseDTO.builder().build();
    }
}
