package com.esthetic.servicesmicroservices.repository;

import com.esthetic.servicesmicroservices.entity.ScheduleService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ScheduleServiceRepository extends JpaRepository<ScheduleService, Long> {
    @Query(value = "SELECT s FROM ScheduleService s LEFT JOIN s.idClient c WHERE s.idProvider.id = ?1 AND s.scheduleDate = ?2 AND (s.idClient IS NULL OR s.idClient.id IN (SELECT u.id FROM User u))")
    List<ScheduleService> findByIdProvider(String idProvider, LocalDateTime date);
    @Query(value = "SELECT s FROM ScheduleService s LEFT JOIN s.idClient c WHERE s.idProvider.id = ?1 AND s.scheduleDate = ?2 AND statusService = ?3 AND (s.idClient IS NULL OR s.idClient.id IN (SELECT u.id FROM User u))")
    List<ScheduleService> findByIdProviderAndStatusService(String idProvider, LocalDateTime date, Integer statusService);
    @Query(value = "SELECT s FROM ScheduleService s WHERE idProvider.id  = ?1 AND scheduleDate = ?2 AND statusService NOT IN (-1, 2)")
    List<ScheduleService> findScheduleByProvider(String idProvider, LocalDateTime date);
    @Query(value = "SELECT s FROM ScheduleService s LEFT JOIN FETCH s.idClient LEFT JOIN FETCH s.idProvider p LEFT JOIN FETCH s.userLocation WHERE idClient.id = ?1 AND scheduleDate = ?2 AND (p IS NOT NULL)")
    List<ScheduleService> findScheduleByClient(String idClient, LocalDateTime date);
//    EL ESTATUS -1 Y 2 SON DE RECHAZADO Y CANCELADO RESPECTIVAMENTE SI SE CAMBIA HAY QUE CAMBIARLO EN FRONT TAMBIEN
    @Query(value = "SELECT * FROM tbl_schedule_service WHERE id_client = ?1 AND schedule_date = ?2 AND start_time < ?4 AND end_time > ?3 AND status_service NOT IN (-1, 2)  LIMIT 1", nativeQuery = true)
    Optional<ScheduleService> findExistSchedule(String idClient,  LocalDateTime date, String startTime, String endTime);
    @Query(value = """
            SELECT ss.id, ss.start_time, u.token_notification, ic.company_name FROM tbl_schedule_service as ss
            LEFT JOIN tbl_user AS u ON ss.id_client = u.id
            LEFT JOIN tbl_info_company AS ic ON ic.id_user = ss.id_provider
            WHERE (schedule_date\\:\\:date + start_time\\:\\:time) BETWEEN NOW()
                AND (NOW() + INTERVAL '4 hours')
                AND ss.send_notification = false AND ss.id_client IS NOT NULL AND status_service = 1;
            """, nativeQuery = true)
    List<Object[]> findServicesToSendNotification();
    @Transactional
    @Modifying
    @Query(value = "UPDATE ScheduleService SET sendNotification = true WHERE id = ?1")
    void updateSendNotification(Long id);
}
