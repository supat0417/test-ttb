package com.crm.crm_service.repository;

import com.crm.crm_service.model.entity.CustomerInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerInfoRepository extends JpaRepository<CustomerInfoEntity, Integer> {
}
