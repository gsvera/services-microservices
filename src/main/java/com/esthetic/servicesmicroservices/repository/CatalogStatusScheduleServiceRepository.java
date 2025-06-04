package com.esthetic.servicesmicroservices.repository;

import com.esthetic.servicesmicroservices.entity.CatalogStatusScheduleService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CatalogStatusScheduleServiceRepository extends JpaRepository<CatalogStatusScheduleService, Long> {
    @Query(value = "SELECT * FROM tbl_catalog_status_schedule_service WHERE status_value = ?1", nativeQuery = true)
    Optional<CatalogStatusScheduleService> findByStatusValue(Integer statusValue);
}
