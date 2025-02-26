package com.crm.crm_service.controller;

import com.crm.crm_service.model.CustomerInfoRequest;
import com.crm.crm_service.model.CustomerInfoResponse;
import com.crm.crm_service.model.CustomerInquiryResponse;
import com.crm.crm_service.model.CustomerSortingResponse;
import com.crm.crm_service.model.SaleRequest;
import com.crm.crm_service.model.SalesResponse;
import com.crm.crm_service.service.CustomerInfoService;
import com.crm.crm_service.util.BusinessExeption;
import com.crm.crm_service.util.ResponseModel;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/customer-info")
public class CustomerInfoController {

    private final CustomerInfoService customerInfoService;

    @Autowired
    public CustomerInfoController(CustomerInfoService customerInfoService) {
        this.customerInfoService = customerInfoService;
    }

    @PostMapping("/customer/register")
    public ResponseModel<CustomerInfoResponse> insertCustomerInfo(@RequestBody @Valid CustomerInfoRequest request) {
        try {
            return new ResponseModel<>(ResponseModel.StatusCode.SUCCESS,
                    customerInfoService.insertCustomerInfo(request));
        } catch (BusinessExeption e) {
            return new ResponseModel<>(e.getStatusCode().getCode(),e.getMessage(), null);
        }
    }

    @PostMapping("/sale")
    public ResponseModel<SalesResponse> insertSale(@RequestBody @Valid SaleRequest request) {
        try {
            return new ResponseModel<>(ResponseModel.StatusCode.SUCCESS,
                    customerInfoService.insertSale(request));
        } catch (BusinessExeption e) {
            return new ResponseModel<>(e.getStatusCode().getCode(),e.getMessage(), null);
        }
    }

    @GetMapping("/customer/inquiry")
    public ResponseModel<List<CustomerInquiryResponse>> getCustomerInfoInquiry(@RequestParam(required = false) Integer customerId) {
        try {
            return new ResponseModel<>(ResponseModel.StatusCode.SUCCESS,
                    customerInfoService.getCustomerInfo(customerId));
        } catch (BusinessExeption e) {
            return new ResponseModel<>(e.getStatusCode().getCode(),e.getMessage(), null);
        }
    }

    @GetMapping("/customer/inquiry/sorting")
    public ResponseModel<List<CustomerSortingResponse>> getCustomerInfoInquirySort(@RequestParam(required = false) Integer customerId) {
        try {
            return new ResponseModel<>(ResponseModel.StatusCode.SUCCESS,
                    customerInfoService.getCustomerSortingByYear());
        } catch (BusinessExeption e) {
            return new ResponseModel<>(e.getStatusCode().getCode(),e.getMessage(), null);
        }
    }


}
