package com.esthetic.servicesmicroservices.services;

import com.esthetic.servicesmicroservices.config.EnvConfig;
import com.esthetic.servicesmicroservices.dto.ResponseDTO;
import com.esthetic.servicesmicroservices.utils.ApiHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final ApiHelper apiHelper;
    @Autowired
    private EnvConfig envConfig;
    public ResponseDTO _ValidIsActiveProvider(String token, String idProvider){
        String api = envConfig.getApiGateway() + "/api/esthetic/auth-user/get-provider-is-active/"+idProvider;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        HttpEntity httpEntity = new HttpEntity<>(headers);
        return apiHelper._RequestedApi(api, "GET", httpEntity, false);
    }
}
