package com.esthetic.servicesmicroservices.repository;

import com.esthetic.servicesmicroservices.entity.MenuService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuServiceRepository extends JpaRepository<MenuService, Long> {
    List<MenuService> findByIdUser(String idUser);
}
