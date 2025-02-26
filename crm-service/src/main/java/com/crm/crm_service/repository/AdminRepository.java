package com.crm.crm_service.repository;

import com.crm.crm_service.model.entity.AdminEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<AdminEntity, Integer> {

    @Modifying
    @Transactional
    @Query(value = "UPDATE admin SET issue_amount = :issueAmount WHERE admin_id = :adminId", nativeQuery = true)
    int updateTaskAdmin(@Param("issueAmount") Integer issueAmount, @Param("adminId") Integer adminId);
}
