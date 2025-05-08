package com.esthetic.servicesmicroservices.repository;

import com.esthetic.servicesmicroservices.entity.UserServicesCalendar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface UserServicesCalendarRepository extends JpaRepository<UserServicesCalendar, Long> {
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM UserServicesCalendar WHERE idUser = ?1")
    int deleteAllCalendarByUser(String idUser);
    @Query(value = "SELECT u FROM UserServicesCalendar u WHERE idUser = ?1")
    List<UserServicesCalendar> getCalendarByUser(String idUser);
    @Query(value = "SELECT c FROM UserServicesCalendar c WHERE idUser = ?1 AND day = ?2")
    Optional<UserServicesCalendar> getTimeByProvider(String idUser, String day);
}
