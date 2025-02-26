package com.crm.crm_service.repository;

import com.crm.crm_service.model.entity.SalesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SaleRepository extends JpaRepository<SalesEntity, Integer> {

    @Query(value = "SELECT s.id, s.customer_id, s.sale_amount, s.sale_date " +
            "FROM sales s " +
            "WHERE s.customer_id = :customerId", nativeQuery = true)
    List<SalesEntity> findOrdersByCustomerId(@Param("customerId") Integer customerId);
}
