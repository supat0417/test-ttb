package com.crm.crm_service.model.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.OffsetDateTime;
import java.util.List;

@Entity
@Data
@Accessors(chain = true)
public class CustomerJoinSaleEntity {

    @Id
    private Integer customerId;

    private String firstName;
    private String lastName;
    private OffsetDateTime customerDate;
    private Boolean isVIP;
    private String statusCode;
    private OffsetDateTime createdOn;
    private OffsetDateTime modifiedOn;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SaleJoinEntity> sales;

}

