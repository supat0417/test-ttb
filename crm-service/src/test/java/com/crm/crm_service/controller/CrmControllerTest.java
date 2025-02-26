package com.crm.crm_service.controller;

import com.crm.crm_service.model.CrmAddIssue;
import com.crm.crm_service.model.GetIssue;
import com.crm.crm_service.model.entity.CrmIssueEntity;
import com.crm.crm_service.service.CrmService;
import com.crm.crm_service.util.BusinessExeption;
import com.crm.crm_service.util.ResponseModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CrmControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CrmService crmService;

    @InjectMocks
    private CrmController crmController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(crmController).build();
    }

    @Test
    void insertIssue_success() throws Exception {
        doNothing().when(crmService).insertIssue(any());

        mockMvc.perform(post("/crm-service/add-issue")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Instancio.create(CrmAddIssue.CrmAddIssueRequest.class))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("1000"));
    }

    @Test
    void insertIssue_fail_business() throws Exception {
        doThrow(new BusinessExeption(ResponseModel.StatusCode.NOTFOUND))
                .when(crmService).insertIssue(any());

        mockMvc.perform(post("/crm-service/add-issue")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Instancio.create(CrmAddIssue.CrmAddIssueRequest.class))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("1001"));
    }

    @Test
    void insertIssue_fail() throws Exception {
        doThrow(new RuntimeException())
                .when(crmService).insertIssue(any());

        mockMvc.perform(post("/crm-service/add-issue")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Instancio.create(CrmAddIssue.CrmAddIssueRequest.class))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("9999"));
    }

    @Test
    void issueInquiry_success() throws Exception {
        when(crmService.issueInquiry(any())).thenReturn(Instancio.ofList(CrmIssueEntity.class).create());

        mockMvc.perform(get("/crm-service/issue-inquiry")
                        .param("issueId", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(ResponseModel.StatusCode.SUCCESS.getCode()))
                .andExpect(jsonPath("$.data").isNotEmpty());
    }

    @Test
    void issueInquiry_fail_bussiness() throws Exception {
        when(crmService.issueInquiry(any())).thenThrow(new BusinessExeption(ResponseModel.StatusCode.NOTFOUND));

        mockMvc.perform(get("/crm-service/issue-inquiry")
                        .param("issueId", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(ResponseModel.StatusCode.NOTFOUND.getCode()))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    void issueInquiry_fail_fail() throws Exception {
        when(crmService.issueInquiry(any())).thenThrow(new RuntimeException());

        mockMvc.perform(get("/crm-service/issue-inquiry")
                        .param("issueId", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(ResponseModel.StatusCode.FAIL.getCode()))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    void getIssue_success() throws Exception {
        when(crmService.issueManage(any())).thenReturn(Instancio.create(GetIssue.GetIssueResponse.class));

        mockMvc.perform(post("/crm-service/get-issue")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Instancio.create(GetIssue.GetIssueRequest.class))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(ResponseModel.StatusCode.SUCCESS.getCode()))
                .andExpect(jsonPath("$.data").isNotEmpty());
    }

    @Test
    void getIssue_fail_bussiness() throws Exception {
        when(crmService.issueManage(any())).thenThrow(new BusinessExeption(ResponseModel.StatusCode.NOTFOUND));

        mockMvc.perform(post("/crm-service/get-issue")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Instancio.create(GetIssue.GetIssueRequest.class))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(ResponseModel.StatusCode.NOTFOUND.getCode()))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    void getIssue_fail_fail() throws Exception {
        when(crmService.issueManage(any())).thenThrow(new RuntimeException());

        mockMvc.perform(post("/crm-service/get-issue")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Instancio.create(GetIssue.GetIssueRequest.class))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(ResponseModel.StatusCode.FAIL.getCode()))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    void updateStatusIssue_success() throws Exception {
        when(crmService.issueManage(any())).thenReturn(Instancio.create(GetIssue.GetIssueResponse.class));

        mockMvc.perform(put("/crm-service/update/status-issue")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Instancio.create(GetIssue.GetIssueRequest.class))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(ResponseModel.StatusCode.SUCCESS.getCode()))
                .andExpect(jsonPath("$.data").isNotEmpty());
    }

    @Test
    void updateStatusIssue_fail_bussiness() throws Exception {
        when(crmService.issueManage(any())).thenThrow(new BusinessExeption(ResponseModel.StatusCode.NOTFOUND));

        mockMvc.perform(put("/crm-service/update/status-issue")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Instancio.create(GetIssue.GetIssueRequest.class))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(ResponseModel.StatusCode.NOTFOUND.getCode()))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    void updateStatusIssue_fail_fail() throws Exception {
        when(crmService.issueManage(any())).thenThrow(new RuntimeException());

        mockMvc.perform(put("/crm-service/update/status-issue")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Instancio.create(GetIssue.GetIssueRequest.class))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(ResponseModel.StatusCode.FAIL.getCode()))
                .andExpect(jsonPath("$.data").isEmpty());
    }

}