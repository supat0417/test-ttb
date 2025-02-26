package com.crm.crm_service.repository;

import com.crm.crm_service.model.entity.CrmIssueEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CrmIssueRepository extends JpaRepository<CrmIssueEntity, Integer> {

    @Modifying
    @Transactional
    @Query(value = "UPDATE crm_issue SET issue_status = :status, admin_id = :adminId WHERE issue_id = :issueId", nativeQuery = true)
    int updateGatIssue(@Param("issueId") Integer issueId, @Param("adminId") Integer adminId, @Param("status") String status);

    @Modifying
    @Transactional
    @Query(value = "UPDATE crm_issue SET issue_status = :status WHERE issue_id = :issueId", nativeQuery = true)
    int updateUpdateIssue(@Param("issueId") Integer issueId,  @Param("status") String status);

}
