package com.crm.crm_service.util;

import lombok.Data;

@Data
public class BusinessExeption extends RuntimeException {
    private ResponseModel.StatusCode statusCode;

    public BusinessExeption(ResponseModel.StatusCode statusCode) {
        super(statusCode.getMessage());
        this.statusCode = statusCode;
    }
}
