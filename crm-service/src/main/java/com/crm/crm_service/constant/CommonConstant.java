package com.crm.crm_service.constant;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class CommonConstant {
    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    public static final String SUBMIT = "SUBMIT";
    public static final String PROGRESS = "PROGRESS";
    public static final String COMPLEATE = "COMPLEATE";
    public static final String REJECT = "REJECT";
    public static final String UPDATE = "UPDATE";
    public static final String GET_ISSUE = "GET_ISSUE";
    public static final List<String> LIST_ACTION_TYPE = List.of(UPDATE, GET_ISSUE);
    public static final List<String> LIST_STATUS = List.of(SUBMIT, PROGRESS, COMPLEATE, REJECT);
}
