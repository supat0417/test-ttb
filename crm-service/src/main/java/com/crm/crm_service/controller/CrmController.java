package com.crm.crm_service.controller;

import com.crm.crm_service.model.CrmAddIssue;
import com.crm.crm_service.model.GetIssue;
import com.crm.crm_service.model.entity.CrmIssueEntity;
import com.crm.crm_service.service.CrmService;
import com.crm.crm_service.util.BusinessExeption;
import com.crm.crm_service.util.ResponseModel;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/crm-service")
public class CrmController {

    private final CrmService crmService;

    public CrmController(CrmService crmService) {
        this.crmService = crmService;
    }

    @PostMapping("/add-issue")
    public ResponseModel<Void> insertIssue(@RequestBody @Valid CrmAddIssue.CrmAddIssueRequest request) {
        try {
            return new ResponseModel<>(ResponseModel.StatusCode.SUCCESS,
                    crmService.insertIssue(request));
        } catch (BusinessExeption e) {
            return new ResponseModel<>(e.getStatusCode().getCode(),e.getMessage(), null);
        } catch (Exception e){
            return new ResponseModel<>(ResponseModel.StatusCode.FAIL, null);
        }
    }

    @GetMapping("/issue-inquiry")
    public ResponseModel<List<CrmIssueEntity>> issueInquiry(@RequestParam(required = false) Integer issueId) {
        try {
            return new ResponseModel<>(ResponseModel.StatusCode.SUCCESS,
                    crmService.issueInquiry(issueId));
        } catch (BusinessExeption e) {
            return new ResponseModel<>(e.getStatusCode().getCode(),e.getMessage(), null);
        }catch (Exception e){
            return new ResponseModel<>(ResponseModel.StatusCode.FAIL, null);
        }
    }

    @PostMapping("/get-issue")
    public ResponseModel<GetIssue.GetIssueResponse> getIssue(@RequestBody @Valid GetIssue.GetIssueRequest request) {
        try {
            return new ResponseModel<>(ResponseModel.StatusCode.SUCCESS,
                    crmService.issueManage(request));
        } catch (BusinessExeption e) {
            return new ResponseModel<>(e.getStatusCode().getCode(),e.getMessage(), null);
        }catch (Exception e){
            return new ResponseModel<>(ResponseModel.StatusCode.FAIL, null);
        }
    }

    @PutMapping("/update/status-issue")
    public ResponseModel<GetIssue.GetIssueResponse> updateStatusIssue(@RequestBody @Valid GetIssue.GetIssueRequest request) {
        try {
            return new ResponseModel<>(ResponseModel.StatusCode.SUCCESS,
                    crmService.issueManage(request));
        } catch (BusinessExeption e) {
            return new ResponseModel<>(e.getStatusCode().getCode(),e.getMessage(), null);
        }catch (Exception e){
            return new ResponseModel<>(ResponseModel.StatusCode.FAIL, null);
        }
    }

}
