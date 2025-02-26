package com.crm.crm_service.model;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

public interface CustomerSaleProjection {
    Integer getCustomerId();
    String getFirstName();
    String getLastName();
    LocalDateTime getCustomerDate();
    Boolean getIsvip();
    String getStatusCode();
    LocalDateTime getCreateOn();
    LocalDateTime getModifiedOn();
    String getId();
    Double getSaleAmount();
    LocalDateTime getSaleDate();
}
