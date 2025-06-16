package com.esthetic.servicesmicroservices.services;

import com.esthetic.servicesmicroservices.dto.ProviderRatingsDTO;
import com.esthetic.servicesmicroservices.dto.ResponseDTO;
import com.esthetic.servicesmicroservices.entity.ProviderRatings;
import com.esthetic.servicesmicroservices.repository.ProviderRatingsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProviderRatingsServices {
    private final ProviderRatingsRepository providerRatingsRepository;

    public void _MakeRatingsByService(ProviderRatings providerRatings) {
        try{
            providerRatingsRepository.save(providerRatings);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    public ResponseDTO _UpdateRatingByService(ProviderRatingsDTO providerRatingsDTO) {
        Optional<ProviderRatings> providerRatings = providerRatingsRepository.findById(providerRatingsDTO.id);
        if(providerRatings.isPresent()) {
            providerRatings.orElseThrow().setRating(providerRatingsDTO.rating);
            providerRatings.orElseThrow().setComment(providerRatingsDTO.comment);
            providerRatings.orElseThrow().setIsPending(false);
            providerRatings.orElseThrow().setUpdateAt(Timestamp.from(Instant.now()));
            providerRatingsRepository.save(providerRatings.get());
            return ResponseDTO.builder().message("Se califico con éxito").build();
        }
        return ResponseDTO.builder().error(true).message("No se encontro el registro").build();
    }
    public ResponseDTO _GetPendingRatingByUser(String idUser) {
        Optional<ProviderRatings> providerRatings = providerRatingsRepository.findByIdUserIsPending(idUser).stream().findFirst();
        if(providerRatings.isPresent()) {
            return ResponseDTO.builder().items(new ProviderRatingsDTO(providerRatings.get())).build();
        }
        return ResponseDTO.builder().message("No hay calificaciones pendientes").build();
    }
    public void _DeleteRatingByService(Long id) {
        Optional<ProviderRatings> providerRatings = providerRatingsRepository.findById(id);
        if(providerRatings.isPresent()) {
            providerRatingsRepository.delete(providerRatings.get());
        }
    }
    public ResponseDTO _GetRatingsByProvider(String idProvider) {
        List<ProviderRatingsDTO> listRatings = providerRatingsRepository.findByIdProvider(idProvider);
        return ResponseDTO.builder().items(listRatings).build();
    }
}
