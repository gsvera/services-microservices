package com.esthetic.servicesmicroservices.services;

import com.esthetic.servicesmicroservices.dto.ResponseDTO;
import com.esthetic.servicesmicroservices.dto.TempClientDTO;
import com.esthetic.servicesmicroservices.entity.TempClient;
import com.esthetic.servicesmicroservices.repository.TempClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TempClientServices {
    private final TempClientRepository tempClientRepository;
    public ResponseDTO _GetTempClientsByProvider(String idProvider) {
        List<TempClient> listTempClient = tempClientRepository.findByIdProviderOrderByTempNameClientAsc(idProvider);
        return ResponseDTO.builder().items(listTempClient.stream().map(item -> new TempClientDTO(item))).build();
    }
    public ResponseDTO _DeleteTempContactByProvider(String idProvider, Long idTempContact) {
        Optional<TempClient> tempClient = tempClientRepository.findTempClientByProvider(idTempContact, idProvider);
        if(tempClient.isPresent()) {
            tempClientRepository.deleteById(tempClient.get().getId());
            return ResponseDTO.builder().message("Contacto eliminado correctamente").build();
        } else {
            return ResponseDTO.builder().error(true).message("No se encontro el registro").build();
        }
    }
    public void _DeleteAllTempContactByProvider(String idProvider) {
        tempClientRepository.deleteAllContactsByProvider(idProvider);
    }
}
