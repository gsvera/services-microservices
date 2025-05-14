package com.esthetic.servicesmicroservices.repository;

import com.esthetic.servicesmicroservices.entity.ScheduleService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface ScheduleServiceRepository extends JpaRepository<ScheduleService, Long> {
    @Query(value = "SELECT s FROM ScheduleService s LEFT JOIN FETCH s.idClient WHERE s.idProvider = ?1 AND s.scheduleDate = ?2 ")
    List<ScheduleService> findByIdProvider(String idProvider, LocalDateTime date);
    @Query(value = "SELECT s FROM ScheduleService s LEFT JOIN FETCH s.idClient WHERE s.idProvider = ?1 AND s.scheduleDate = ?2 AND statusService = ?3")
    List<ScheduleService> findByIdProviderAndStatusService(String idProvider, LocalDateTime date, Integer statusService);
}
