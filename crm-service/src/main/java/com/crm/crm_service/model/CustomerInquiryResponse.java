package com.crm.crm_service.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class CustomerInquiryResponse {
    private Integer customerId;
    private String firstName;
    private String lastName;
    private String customerDate;
    private Boolean isVIP;
    private String statusCode;
    private String createdOn;
    private String modifiedOn;
    private List<Sale> sales;

    @Data
    @Accessors(chain = true)
    public static class Sale{
        private Double saleAmount;
        private String saleDate;
    }
}
