package com.crm.crm_service.repository;

import com.crm.crm_service.model.CustomerSaleProjection;
import com.crm.crm_service.model.CustomerSorting;
import com.crm.crm_service.model.entity.CustomerJoinSaleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerInfoJoinSaleRepository extends JpaRepository<CustomerJoinSaleEntity, Integer> {

    @Query(value = """
            SELECT c.customer_id AS customerId, 
                   c.first_name AS firstName, 
                   c.last_name AS lastName, 
                   c.customer_date AS customerDate, 
                   c.isvip, 
                   c.status_code AS statusCode, 
                   c.created_on AS createdOn, 
                   c.modified_on AS modifiedOn, 
                   s.id, 
                   s.sale_amount AS saleAmount, 
                   s.sale_date AS saleDate 
            FROM customer_info c 
            LEFT JOIN sales s ON c.customer_id = s.customer_id 
            WHERE c.customer_id = :customerId
            """, nativeQuery = true)
    List<CustomerSaleProjection> findCustomerByCustomerId(@Param("customerId") Integer customerId);

    @Query(value = """
            SELECT c.customer_id AS customerId, 
                   c.first_name AS firstName, 
                   c.last_name AS lastName, 
                   c.customer_date AS customerDate, 
                   c.isvip, 
                   c.status_code AS statusCode, 
                   c.created_on AS createdOn, 
                   c.modified_on AS modifiedOn, 
                   s.id, 
                   s.sale_amount AS saleAmount, 
                   s.sale_date AS saleDate 
            FROM customer_info c 
            LEFT JOIN sales s ON c.customer_id = s.customer_id 
            """, nativeQuery = true)
    List<CustomerSaleProjection> findCustomerAll();


    @Query(value = """
            SELECT
                YEAR(s.sale_date) AS saleYear,
                c.first_name AS firstName ,
                c.last_name as lastName,
                SUM(s.sale_amount) AS totalSales
            FROM test_ttb.sales s
            JOIN test_ttb.customer_info c ON s.customer_id = c.customer_id
            GROUP BY saleYear, c.first_name, c.last_name
            ORDER BY saleYear ASC, totalSales DESC 
            """, nativeQuery = true)
    List<CustomerSorting> findCustomerSorting();
}
