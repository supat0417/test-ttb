package com.crm.crm_service.service;

import com.crm.crm_service.model.CrmAddIssue;
import com.crm.crm_service.model.GetIssue;
import com.crm.crm_service.model.entity.AdminEntity;
import com.crm.crm_service.model.entity.CrmIssueEntity;
import com.crm.crm_service.repository.AdminRepository;
import com.crm.crm_service.repository.CrmIssueRepository;
import com.crm.crm_service.util.BusinessExeption;
import com.crm.crm_service.util.ResponseModel;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;
import java.util.Optional;

import static com.crm.crm_service.constant.CommonConstant.GET_ISSUE;
import static com.crm.crm_service.constant.CommonConstant.PROGRESS;
import static com.crm.crm_service.constant.CommonConstant.SUBMIT;
import static com.crm.crm_service.constant.CommonConstant.UPDATE;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest()
class CrmServiceTest {

    @MockitoBean
    private CrmIssueRepository crmIssueRepository;
    @MockitoBean
    private AdminRepository adminRepository;

    @Autowired
    private CrmService crmService;


    @Test
    void insertIssue_caseSuccess() {
        when(crmIssueRepository.save(Instancio.create(CrmIssueEntity.class))).thenReturn(Instancio.create(CrmIssueEntity.class));
        assertDoesNotThrow(() -> crmService.insertIssue(Instancio.create(CrmAddIssue.CrmAddIssueRequest.class)));
    }

    @Test
    void issueInquiry_findAll_caseSuccess() {
        List<CrmIssueEntity> mock = Instancio.ofList(CrmIssueEntity.class).size(2).create();
        when(crmIssueRepository.findAll()).thenReturn(mock);
        List<CrmIssueEntity> result = crmService.issueInquiry(null);
        assertEquals(mock, result);
    }

    @Test
    void issueInquiry_findById_caseSuccess() {
        CrmIssueEntity mock = Instancio.of(CrmIssueEntity.class).create();
        when(crmIssueRepository.findById(any())).thenReturn(Optional.of(mock));
        List<CrmIssueEntity> result = crmService.issueInquiry(1);
        assertEquals(List.of(mock), result);
    }

    @Test
    void issueManage_statusType_PROGRESS_Success() {
        AdminEntity mockAdmin = Instancio.of(AdminEntity.class).create().setIssueAmount(2);
        when(adminRepository.findById(any())).thenReturn(Optional.of(mockAdmin));

        CrmIssueEntity mockCus = Instancio.of(CrmIssueEntity.class).create().setIssueStatus(SUBMIT);
        when(crmIssueRepository.findById(any())).thenReturn(Optional.of(mockCus));

        when(crmIssueRepository.updateGatIssue(any(), any(), any())).thenReturn(0);
        when(adminRepository.updateTaskAdmin(any(), any())).thenReturn(0);

        GetIssue.GetIssueResponse result = crmService.issueManage(new GetIssue.GetIssueRequest()
                .setIssueId(1)
                .setAdminId(1)
                .setActionType(GET_ISSUE)
                .setStatusSelected(SUBMIT));

        assertEquals(new GetIssue.GetIssueResponse()
                .setIssueId(1)
                .setStatusSelected(SUBMIT), result);
    }

    @Test
    void issueManage_statusType_UPDATE_Success() {
        AdminEntity mockAdmin = Instancio.of(AdminEntity.class).create().setIssueAmount(2);
        when(adminRepository.findById(any())).thenReturn(Optional.of(mockAdmin));

        CrmIssueEntity mockCus = Instancio.of(CrmIssueEntity.class).create().setIssueStatus(PROGRESS);
        when(crmIssueRepository.findById(any())).thenReturn(Optional.of(mockCus));

        when(crmIssueRepository.updateGatIssue(any(), any(), any())).thenReturn(0);
        when(adminRepository.updateTaskAdmin(any(), any())).thenReturn(0);

        GetIssue.GetIssueResponse result = crmService.issueManage(new GetIssue.GetIssueRequest()
                .setIssueId(1)
                .setAdminId(1)
                .setActionType(UPDATE)
                .setStatusSelected(PROGRESS));

        assertEquals(new GetIssue.GetIssueResponse()
                .setIssueId(1)
                .setStatusSelected(PROGRESS), result);
    }

    @Test
    void issueManage_statusType_PROGRESS_Fail_validate() {
        AdminEntity mockAdmin = Instancio.of(AdminEntity.class).create().setIssueAmount(3);
        when(adminRepository.findById(any())).thenReturn(Optional.of(mockAdmin));

        CrmIssueEntity mockCus = Instancio.of(CrmIssueEntity.class).create().setIssueStatus(SUBMIT);
        when(crmIssueRepository.findById(any())).thenReturn(Optional.of(mockCus));

        BusinessExeption result =
                assertThrows(BusinessExeption.class, () ->
                        crmService.issueManage(new GetIssue.GetIssueRequest()
                                .setIssueId(1)
                                .setAdminId(1)
                                .setActionType(GET_ISSUE)
                                .setStatusSelected(SUBMIT)));

        assertEquals(ResponseModel.StatusCode.FAIL.getCode(), result.getStatusCode().getCode());
    }

    @Test
    void issueManage_statusType_UPDATE_Fail_validate() {
        AdminEntity mockAdmin = Instancio.of(AdminEntity.class).create().setIssueAmount(0);
        when(adminRepository.findById(any())).thenReturn(Optional.of(mockAdmin));

        CrmIssueEntity mockCus = Instancio.of(CrmIssueEntity.class).create().setIssueStatus(PROGRESS);
        when(crmIssueRepository.findById(any())).thenReturn(Optional.of(mockCus));

        BusinessExeption result =
                assertThrows(BusinessExeption.class, () ->
                        crmService.issueManage(new GetIssue.GetIssueRequest()
                                .setIssueId(1)
                                .setAdminId(1)
                                .setActionType(UPDATE)
                                .setStatusSelected(PROGRESS)));

        assertEquals(ResponseModel.StatusCode.FAIL.getCode(), result.getStatusCode().getCode());
    }

    @Test
    void issueManage_statusType_Fail_Notfould() {
        when(adminRepository.findById(any())).thenReturn(Optional.empty());
        when(crmIssueRepository.findById(any())).thenReturn(Optional.empty());

        BusinessExeption result =
                assertThrows(BusinessExeption.class, () ->
                        crmService.issueManage(new GetIssue.GetIssueRequest()
                                .setIssueId(1)
                                .setAdminId(1)
                                .setActionType(UPDATE)
                                .setStatusSelected(PROGRESS)));

        assertEquals(ResponseModel.StatusCode.NOTFOUND.getCode(), result.getStatusCode().getCode());
    }


}