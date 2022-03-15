package com.project.account.service;

import com.project.account.model.AccountRole;

public interface RoleService {
    AccountRole getAccountRoleById(long id);

    AccountRole getAccountRoleByRoleName(String name);
}
