package com.crm.crm_service.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.OffsetDateTime;

@Entity
@Table(name = "customer_info")
@Data
@Accessors(chain = true)
public class CustomerInfoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer customerId;

    private String firstName;
    private String lastName;
    private OffsetDateTime customerDate;
    private Boolean isVIP;
    private String statusCode;
    private OffsetDateTime createdOn;
    private OffsetDateTime modifiedOn;
}
