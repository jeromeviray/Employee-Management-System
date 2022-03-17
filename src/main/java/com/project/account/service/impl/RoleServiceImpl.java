package com.project.account.service.impl;

import com.project.account.model.AccountRole;
import com.project.account.repository.RoleRepository;
import com.project.account.service.RoleService;

import java.util.Optional;

public class RoleServiceImpl implements RoleService {
    private RoleRepository roleRepository;

    public RoleServiceImpl() {
    }

    public RoleServiceImpl( RoleRepository roleRepository ) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Optional<AccountRole> getAccountRoleById( long id ) {
        Optional<AccountRole> role = roleRepository.findById( id );
//        if(role.isEmpty()) throw new NotFoundException(String.format( "Role not Found. Role ID: %s", id ));
        return role;
    }

    @Override
    public Optional<AccountRole> getAccountRoleByRoleName( String name ) {
        Optional<AccountRole> role = roleRepository.findByRoleName( name );
//        if(role.isEmpty()) throw new NotFoundException(String.format( "Role not Found. Role Name: %s", name ));
        return role;
    }
}
