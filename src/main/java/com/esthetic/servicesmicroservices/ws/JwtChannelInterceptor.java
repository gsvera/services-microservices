package com.esthetic.servicesmicroservices.ws;

import com.esthetic.servicesmicroservices.services.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtChannelInterceptor implements ChannelInterceptor {
    private final JwtService jwtService;
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        try{
            StompHeaderAccessor accessor = StompHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
            if(StompCommand.CONNECT.equals(accessor.getCommand())) {
                String token = accessor.getFirstNativeHeader("Authorization");
                if(token != null && token.startsWith("Bearer ")) {
                    token = token.substring(7);
                    String userName = jwtService.getUsernameFromToken(token);
                    accessor.setUser(new UsernamePasswordAuthenticationToken(userName, null, List.of()));
                }
            }
        } catch (Exception ex) {
            System.out.println("Error in preSend: " + ex.getMessage());
            ex.printStackTrace();
        }
        return message;
    }
}
