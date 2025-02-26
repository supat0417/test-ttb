package com.crm.crm_service.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CrmAddIssue {

    @Data
    @Accessors(chain = true)
    public static class CrmAddIssueRequest{
        private String customerId;
        private String issueType;
        private String issueDesc;
    }
}
