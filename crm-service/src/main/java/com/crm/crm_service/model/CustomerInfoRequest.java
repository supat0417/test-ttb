package com.crm.crm_service.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.OffsetDateTime;

@Data
@Accessors(chain = true)
public class CustomerInfoRequest {
    @NotEmpty
    private String firstName;
    @NotEmpty
    private String lastName;
    private OffsetDateTime customerDate;
    private Boolean isVIP;
    @NotEmpty
    private String statusCode;

    private SaleRequest sale;

}
