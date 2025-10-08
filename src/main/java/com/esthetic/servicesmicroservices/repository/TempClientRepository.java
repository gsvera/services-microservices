package com.esthetic.servicesmicroservices.repository;

import com.esthetic.servicesmicroservices.entity.TempClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface TempClientRepository extends JpaRepository<TempClient, Long> {
    @Query(value = "SELECT * FROM tbl_temp_client WHERE temp_phone_client = ?1 AND id_provider = ?2 LIMIT 1", nativeQuery = true)
    Optional<TempClient> findExistTempClient(String tempPhoneClient, String idProvider);
    List<TempClient> findByIdProviderOrderByTempNameClientAsc(String idProvider);
    @Query(value = "SELECT t FROM TempClient t WHERE t.id = ?1 AND t.idProvider = ?2")
    Optional<TempClient> findTempClientByProvider(Long id, String idProvider);
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM TempClient t WHERE t.idProvider = ?1")
    void deleteAllContactsByProvider(String idProvider);
}
