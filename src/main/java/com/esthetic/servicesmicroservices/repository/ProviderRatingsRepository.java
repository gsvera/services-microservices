package com.esthetic.servicesmicroservices.repository;

import com.esthetic.servicesmicroservices.dto.ProviderRatingsDTO;
import com.esthetic.servicesmicroservices.entity.ProviderRatings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProviderRatingsRepository extends JpaRepository<ProviderRatings, Long> {
    @Query(value = "SELECT r FROM ProviderRatings r LEFT JOIN FETCH r.idProvider LEFT JOIN FETCH r.idProvider.userInfoCompany WHERE idUser.id = ?1 and isPending = true")
    List<ProviderRatings> findByIdUserIsPending(String idUser);
    @Query(value = "SELECT new com.esthetic.servicesmicroservices.dto.ProviderRatingsDTO(r.id, r.rating, r.comment, r.createdAt, u.firstName, u.lastName) FROM ProviderRatings r LEFT JOIN r.idUser u WHERE isPending = false AND show = true AND r.idProvider.id = ?1")
    List<ProviderRatingsDTO> findByIdProvider(String idProvider);
}
