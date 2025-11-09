package com.esthetic.servicesmicroservices.dto;

import com.esthetic.servicesmicroservices.entity.ShareCalendar;

import java.time.Instant;

public class ShareCalendarDTO {
    public Long id;
    public String idProvider;
    private String token;
    private Instant createdAt;
    public ShareCalendarDTO(ShareCalendar shareCalendar) {
        this.id = shareCalendar.getId();
        this.idProvider = shareCalendar.getIdProvider();
        this.token = shareCalendar.getToken();
        this.createdAt = shareCalendar.getCreatedAt();
    }
}
