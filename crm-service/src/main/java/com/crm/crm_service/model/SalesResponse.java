package com.crm.crm_service.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.OffsetDateTime;

@Data
@Accessors(chain = true)
public class SalesResponse {

    private Integer customerId;
    private Double saleAmount;
    private OffsetDateTime saleDate;
}
