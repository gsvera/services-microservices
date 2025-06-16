package com.esthetic.servicesmicroservices.repository;

import com.esthetic.servicesmicroservices.entity.MenuService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface MenuServiceRepository extends JpaRepository<MenuService, Long> {
    List<MenuService> findByIdUser(String idUser);
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM MenuService WHERE idUser = ?1")
    void deleteAllByUser(String idUser);
}
