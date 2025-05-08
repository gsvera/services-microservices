package com.esthetic.servicesmicroservices.repository;

import com.esthetic.servicesmicroservices.entity.ScheduleService;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleServiceRepository extends JpaRepository<ScheduleService, Long> {
}
