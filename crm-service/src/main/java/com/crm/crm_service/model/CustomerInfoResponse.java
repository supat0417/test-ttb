package com.crm.crm_service.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.OffsetDateTime;

@Data
@Accessors(chain = true)
public class CustomerInfoResponse {
    private Integer customerId;
    private String firstName;
    private String lastName;
    private OffsetDateTime customerDate;
    private Boolean isVIP;
    private String statusCode;
    private OffsetDateTime createdOn;
    private OffsetDateTime modifiedOn;

    private SalesResponse sale;
}
