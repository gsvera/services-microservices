package com.esthetic.servicesmicroservices.repository;

import com.esthetic.servicesmicroservices.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findById(String id);
    @Query(value = """
                SELECT 
                    u.id, ic.company_name, ic.company_picture_url, ul.latitude, ul.longitude, ul.aux_state, ul.aux_municipality, ul.reference 
                FROM tbl_share_calendar AS sc
                LEFT JOIN tbl_user AS u ON sc.id_provider = u.id
                LEFT JOIN tbl_info_company AS ic ON u.id = ic.id_user
                LEFT JOIN tbl_user_location AS ul ON u.id = ul.id_user
                WHERE sc.token = ?1
            """, nativeQuery = true)
    List<Object[]> GetPublicInfoProvider(String shareTokenCalendar);
}
