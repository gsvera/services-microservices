package com.esthetic.servicesmicroservices.services;

import com.esthetic.servicesmicroservices.dto.NotificationDTO;
import com.esthetic.servicesmicroservices.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final SimpMessagingTemplate messagingTemplate;
    private final UserRepository userRepository;
    public void sendPrivateNotification(String userId, NotificationDTO notificationDTO) {
        try{
            messagingTemplate.convertAndSendToUser(userId, "/topic/queue/services", notificationDTO);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }
    public String getUserName(String userId) {
        return userRepository.findById(userId).get().getEmail();
    }
}
