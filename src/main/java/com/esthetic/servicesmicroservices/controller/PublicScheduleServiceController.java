package com.esthetic.servicesmicroservices.controller;

import com.esthetic.servicesmicroservices.dto.ResponseDTO;
import com.esthetic.servicesmicroservices.services.ProviderRatingsServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/esthetic/public-schedule-service")
public class PublicScheduleServiceController {
    @Autowired
    private ProviderRatingsServices providerRatingsServices;
    @GetMapping("/get-ratings-by-provider/{id-provider}")
    public ResponseDTO GetRatingsByProvider(@PathVariable(name = "id-provider") String idProvider) {
        try{
            return providerRatingsServices._GetRatingsByProvider(idProvider);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return ResponseDTO.builder().error(true).message("Ocurrio un error intentelo mas tarde").build();
        }
    }
}
