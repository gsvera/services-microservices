package com.esthetic.servicesmicroservices.controller;

import com.esthetic.servicesmicroservices.dto.NotificationDTO;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;


// Este es un ejemplo en el caso de que la comunicacion sea directa de front a front y no requiera hacer ningun
// cambio en la base de datos, ahora no se me ocurre como para que pero lo dejo aqui por el momento9
@Controller
public class NotificationController {
    private final SimpMessagingTemplate messagingTemplate;

    public NotificationController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    // Emitir evento a todos
    public void sendReservationUpdate(String reservationId) {
//        messagingTemplate.convertAndSend("/topic/reservations", new NotificationDTO("updated", reservationId));
    }

    // Emitir evento a un usuario en específico
    public void sendPrivateNotification(String userId, NotificationDTO notificationDTO) {
        messagingTemplate.convertAndSendToUser(userId, "/queue/notifications", notificationDTO);
    }
}
