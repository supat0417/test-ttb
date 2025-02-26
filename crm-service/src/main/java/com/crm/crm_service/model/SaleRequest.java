package com.crm.crm_service.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class SaleRequest {
    private Integer customerId;
    private Double saleAmount;
    private String saleDate;
}
