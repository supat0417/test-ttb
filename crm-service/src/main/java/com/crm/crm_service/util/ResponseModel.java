package com.crm.crm_service.util;

public class ResponseModel<T> {
    private String code;
    private String message;
    private T data;

    public ResponseModel(StatusCode status, T data) {
        this.code = status.code;
        this.message = status.message;
        this.data = data;
    }

    public ResponseModel(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public enum StatusCode {
        SUCCESS("1000","SUCCESS"),
        NOTFOUND("1001","DATA NOT FOUND"),
        FAIL("9999","FAIL");

        private final String code;
        private final String message;

        public String getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }

        StatusCode(String code, String message) {
            this.code = code;
            this.message = message;
        }
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }
}
