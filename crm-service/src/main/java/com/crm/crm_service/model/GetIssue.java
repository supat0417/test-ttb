package com.crm.crm_service.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.validation.annotation.Validated;

import static com.crm.crm_service.constant.CommonConstant.LIST_ACTION_TYPE;
import static com.crm.crm_service.constant.CommonConstant.LIST_STATUS;

@Data
@Accessors(chain = true)
@Validated
public class GetIssue {

    @Data
    @Accessors(chain = true)
    @Valid
    public static class GetIssueRequest {
        @NotNull
        private Integer issueId;
        @NotNull
        private Integer adminId;
        @NotEmpty
        private String statusSelected;
        @NotEmpty
        private String actionType;

        @AssertTrue(message = "statusSelected is not actionType")
        public boolean validateActionType() {
            return LIST_ACTION_TYPE.contains(this.actionType);
        }

        @AssertTrue(message = "statusSelected is not match")
        public boolean validateStatus() {
            return LIST_STATUS.contains(this.statusSelected);
        }
    }

    @Data
    @Accessors(chain = true)
    public static class GetIssueResponse {
        private Integer issueId;
        private String statusSelected;
    }

}
