package com.project.account.service;

import com.project.account.model.AccountRole;

import java.util.Optional;

public interface RoleService {
    Optional<AccountRole> getAccountRoleById( long id);

    Optional<AccountRole> getAccountRoleByRoleName(String name);
}
