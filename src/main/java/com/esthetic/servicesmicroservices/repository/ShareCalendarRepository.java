package com.esthetic.servicesmicroservices.repository;

import com.esthetic.servicesmicroservices.entity.ShareCalendar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShareCalendarRepository extends JpaRepository<ShareCalendar, Long> {
    Optional<ShareCalendar> findByIdProvider(String idProvider);
    Optional<ShareCalendar> findByToken(String token);
}