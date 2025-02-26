package com.crm.crm_service.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.OffsetDateTime;

@Entity
@Data
@Accessors(chain = true)
public class SaleJoinEntity {

    @Id
    private Integer id;

    private Double saleAmount;
    private OffsetDateTime saleDate;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private CustomerJoinSaleEntity customer;

}

