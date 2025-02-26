package com.crm.crm_service.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class CustomerSortingResponse {
    private String year;
    private List<Customer> customers;

    @Data
    @Accessors(chain = true)
    public static class Customer{
        private String firstName;
        private String lastName;
        private Double totalAmount;

    }
}
