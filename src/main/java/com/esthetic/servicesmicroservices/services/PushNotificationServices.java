package com.esthetic.servicesmicroservices.services;

import com.esthetic.servicesmicroservices.dto.ResponseDTO;
import com.esthetic.servicesmicroservices.entity.User;
import com.esthetic.servicesmicroservices.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class PushNotificationServices {
    private final UserRepository userRepository;
//
//    public ResponseDTO prueba(String id) {
//        Optional<User> user = userRepository.findById(id);
//        if(user.isPresent()) {
//            this.sendPushNotification(user.get().getTokenNotification(), "Prueba", "body");
//            return ResponseDTO.builder().message(user.get().getTokenNotification()).build();
//        }
//        return ResponseDTO.builder().error(true).message("no hay usuario").build();
//    }
    public void sendPushNotification(String tokenNotification, String title, String body) {
            String expoApiUrl = "https://exp.host/--/api/v2/push/send";

            RestTemplate restTemplate = new RestTemplate();

            Map<String, Object> payload = new HashMap<>();
            payload.put("to", tokenNotification); // Token tipo "ExponentPushToken[xxxxxxx]"
            payload.put("title", title);
            payload.put("body", body);
            payload.put("sound", "default");

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Accept", "application/json");

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(payload, headers);

            try {
                ResponseEntity<String> response = restTemplate.postForEntity(expoApiUrl, request, String.class);
                System.out.println("Expo response: " + response.getBody());
            } catch (Exception e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
    }
}
