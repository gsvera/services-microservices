package com.esthetic.servicesmicroservices.repository;

import com.esthetic.servicesmicroservices.entity.ScheduleService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ScheduleServiceRepository extends JpaRepository<ScheduleService, Long> {
    @Query(value = "SELECT s FROM ScheduleService s LEFT JOIN FETCH s.idClient WHERE idProvider.id = ?1 AND s.scheduleDate = ?2 ")
    List<ScheduleService> findByIdProvider(String idProvider, LocalDateTime date);
    @Query(value = "SELECT s FROM ScheduleService s LEFT JOIN FETCH s.idClient WHERE idProvider.id = ?1 AND s.scheduleDate = ?2 AND statusService = ?3")
    List<ScheduleService> findByIdProviderAndStatusService(String idProvider, LocalDateTime date, Integer statusService);
    @Query(value = "SELECT s FROM ScheduleService s WHERE idProvider.id  = ?1 AND scheduleDate = ?2 AND statusService NOT IN (-1, 2)")
    List<ScheduleService> findScheduleByProvider(String idProvider, LocalDateTime date);
    @Query(value = "SELECT s FROM ScheduleService s LEFT JOIN FETCH s.idClient LEFT JOIN FETCH s.idProvider LEFT JOIN FETCH s.userLocation WHERE idClient.id = ?1 AND scheduleDate = ?2")
    List<ScheduleService> findScheduleByClient(String idClient, LocalDateTime date);
//    EL ESTATUS -1 Y 2 SON DE RECHAZADO Y CANCELADO RESPECTIVAMENTE SI SE CAMBIA HAY QUE CAMBIARLO EN FRONT TAMBIEN
    @Query(value = "SELECT * FROM tbl_schedule_service WHERE id_client = ?1 AND schedule_date = ?2 AND start_time < ?4 AND end_time > ?3 AND status_service NOT IN (-1, 2)  LIMIT 1", nativeQuery = true)
    Optional<ScheduleService> findExistSchedule(String idClient,  LocalDateTime date, String startTime, String endTime);
}
