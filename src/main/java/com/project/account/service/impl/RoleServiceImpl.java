package com.project.account.service.impl;

import com.project.account.model.AccountRole;
import com.project.account.repository.RoleRepository;
import com.project.account.service.RoleService;
import com.project.execption.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public AccountRole getAccountRoleById( long id ) {
        Optional<AccountRole> role = roleRepository.findById( id );
        if(role.isEmpty()) throw new NotFoundException(String.format( "Role not Found. Role ID: %s", id ));
        return role.get();
    }

    @Override
    public AccountRole getAccountRoleByRoleName( String name ) {
        Optional<AccountRole> role = roleRepository.findByRoleName( name );
        if(role.isEmpty()) throw new NotFoundException(String.format( "Role not Found. Role Name: %s", name ));
        return role.get();
    }
}
