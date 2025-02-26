package com.crm.crm_service.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class IssueInquiryResponse {
    private Integer issueId;
    private String customerId ;
    private String issueType ;
    private String issueStatus ;
    private String adminId ;
}
