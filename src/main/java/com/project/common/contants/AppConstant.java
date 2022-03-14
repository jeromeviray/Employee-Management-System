package com.project.common.contants;

import org.springframework.stereotype.Component;

@Component
public class AppConstant {
    public static final String ENDPOINT_VERSION_ONE = "v1";
    public static final String EMPLOYEES = "/employees";
    public static final String PATH_VARIABLE_ID = "/{id}";
    public static final String PATH_VARIABLE_NAME = "/{name}";

    public static final String DEFAULT_PAGE_NUMBER = "0";
    public  static final String DEFAULT_PAGE_SIZE = "10";
    public static final String DEFAULT_SORT_BY = "id";
    public static final String DEFAULT_ORDER_BY = "asc";
}
