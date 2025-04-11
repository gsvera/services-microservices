package com.esthetic.servicesmicroservices.repository;

import com.esthetic.servicesmicroservices.entity.UserServicesCalendarException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

public interface UserServicesCalendarExceptionRepository extends JpaRepository<UserServicesCalendarException, Long> {
    @Query(value = "SELECT c FROM UserServicesCalendarException c WHERE idUser = ?1 AND dateString = ?2")
    Optional<UserServicesCalendarException> findByIdUserAndDateString(String idUser, LocalDateTime dateTime);
}
