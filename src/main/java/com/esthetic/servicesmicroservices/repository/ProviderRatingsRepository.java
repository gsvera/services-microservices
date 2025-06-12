package com.esthetic.servicesmicroservices.repository;

import com.esthetic.servicesmicroservices.entity.ProviderRatings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProviderRatingsRepository extends JpaRepository<ProviderRatings, Long> {
    @Query(value = "SELECT r FROM ProviderRatings r LEFT JOIN FETCH r.idProvider LEFT JOIN FETCH r.idProvider.userInfoCompany WHERE idUser = ?1 and isPending = true")
    List<ProviderRatings> findByIdUserIsPending(String idUser);
}
