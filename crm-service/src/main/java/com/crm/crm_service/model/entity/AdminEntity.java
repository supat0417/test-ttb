package com.crm.crm_service.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.experimental.Accessors;

@Entity
@Table(name = "admin")
@Data
@Accessors(chain = true)
public class AdminEntity {
    @Id
    private Integer adminId;
    private String adminName;
    private Integer issueAmount;
}
