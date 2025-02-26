package com.crm.crm_service.service;

import com.crm.crm_service.model.CrmAddIssue;
import com.crm.crm_service.model.GetIssue;
import com.crm.crm_service.model.entity.AdminEntity;
import com.crm.crm_service.model.entity.CrmIssueEntity;
import com.crm.crm_service.repository.AdminRepository;
import com.crm.crm_service.repository.CrmIssueRepository;
import com.crm.crm_service.util.BusinessExeption;
import com.crm.crm_service.util.ResponseModel;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.crm.crm_service.constant.CommonConstant.PROGRESS;
import static com.crm.crm_service.constant.CommonConstant.SUBMIT;
import static com.crm.crm_service.constant.CommonConstant.UPDATE;

@Service
@Log4j2
public class CrmService {

    private final CrmIssueRepository crmIssueRepository;
    private final AdminRepository adminRepository;

    @Autowired
    public CrmService(CrmIssueRepository crmIssueRepository, AdminRepository adminRepository) {
        this.crmIssueRepository = crmIssueRepository;
        this.adminRepository = adminRepository;
    }

    public Void insertIssue(CrmAddIssue.CrmAddIssueRequest request) {
        crmIssueRepository.save(new CrmIssueEntity()
                .setCustomerId(request.getCustomerId())
                .setIssueType(request.getIssueType())
                .setIssueStatus(SUBMIT)
        );
        return null;
    }

    public List<CrmIssueEntity> issueInquiry(Integer issueId) {
        List<CrmIssueEntity> response;
        if (ObjectUtils.isEmpty(issueId)) {
            response = crmIssueRepository.findAll().stream().toList();
        } else {
            response = crmIssueRepository.findById(issueId).stream().toList();
        }
        return response;
    }

    public GetIssue.GetIssueResponse issueManage(GetIssue.GetIssueRequest request) {
        AdminEntity adminEntities = adminRepository.findById(request.getAdminId()).stream()
                .findFirst().orElse(null);
        CrmIssueEntity issue = issueInquiry(request.getIssueId()).stream()
                .findFirst().orElse(null);
        validateCondition(adminEntities, issue, request.getActionType());

        if (StringUtils.equals(UPDATE, request.getActionType())) {
            crmIssueRepository.updateUpdateIssue(request.getIssueId(), request.getStatusSelected());
            adminRepository.updateTaskAdmin(adminEntities.getIssueAmount() - 1, request.getAdminId());
        } else {
            crmIssueRepository.updateGatIssue(request.getIssueId(), request.getAdminId(), request.getStatusSelected());
            adminRepository.updateTaskAdmin(adminEntities.getIssueAmount() + 1, request.getAdminId());
        }

        //TODO: inform for customer to process

        return new GetIssue.GetIssueResponse()
                .setIssueId(request.getIssueId())
                .setStatusSelected(request.getStatusSelected());
    }

    private void validateCondition(AdminEntity adminEntities, CrmIssueEntity issue, String actionType) {
        if (ObjectUtils.isEmpty(adminEntities) || ObjectUtils.isEmpty(issue)) {
            log.error("entity null");
            throw new BusinessExeption(ResponseModel.StatusCode.NOTFOUND);
        }
        if (StringUtils.equals(UPDATE, actionType)) {
            if (adminEntities.getIssueAmount() <= 0 || !StringUtils.equals(PROGRESS, issue.getIssueStatus())) {
                log.error("update fail");
                throw new BusinessExeption(ResponseModel.StatusCode.FAIL);
            }
        } else {
            if (adminEntities.getIssueAmount() > 2 || !StringUtils.equals(SUBMIT, issue.getIssueStatus())) {
                log.error("add fail");
                throw new BusinessExeption(ResponseModel.StatusCode.FAIL);
            }
        }


    }
}
