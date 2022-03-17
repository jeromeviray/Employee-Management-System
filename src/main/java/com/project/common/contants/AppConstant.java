package com.project.common.contants;

import org.springframework.stereotype.Component;

@Component
public class AppConstant {
    public static final String ENDPOINT_VERSION_ONE = "/v1";
    public static final String EMPLOYEES = "/employees";
    public static final String ACCOUNTS = "/accounts";
    public static final String PATH_VARIABLE_ID = "/{id}";
    public static final String PATH_VARIABLE_NAME = "/{name}";
    public static final String PATH_VARIABLE_USERNAME = "/{username}";
    public static final String CHANGE_ENDPOINT = "/change";
    public static final String PASSWORD_ENDPOINT = "/password";

    public static final String LOGIN_ENDPOINT = "/login";
    public static final String REQUEST_TOKEN = "/request/token";

    public static final String DEFAULT_PAGE_NUMBER = "0";
    public  static final String DEFAULT_PAGE_SIZE = "10";
    public static final String DEFAULT_SORT_BY = "id";
    public static final String DEFAULT_ORDER_BY = "asc";

    public static final String TYPE = "Bearer ";
    public static final long ACCESS_TOKEN_EXPIRATION_DATE = 3600000 * 24;
    public static final String SECRET_KEY = "secret@Code!@#%^&";
}
