package com.project.account.service.impl;

import com.project.account.model.Account;
import com.project.account.model.AccountDto;
import com.project.account.model.AccountRole;
import com.project.account.model.ChangePassword;
import com.project.account.repository.AccountRepository;
import com.project.account.service.AccountService;
import com.project.account.service.RoleService;
import com.project.execption.InvalidException;
import com.project.execption.NotFoundException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

public class AccountServiceImpl implements AccountService {
    Logger logger = LoggerFactory.getLogger( AccountServiceImpl.class );

    private AccountRepository accountRepository;

    private RoleService roleService;

    private PasswordEncoder passwordEncoder;
    private ModelMapper modelMapper;

    public AccountServiceImpl() {
    }

    public AccountServiceImpl( AccountRepository accountRepository, RoleService roleService, PasswordEncoder passwordEncoder, ModelMapper modelMapper ) {
        this.accountRepository = accountRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }

    @Override
    public void saveAccount( Account account ) {
        Optional<AccountRole> accountRole = getRoleByRoleName( account.getRole().getRoleName() );
        account.setRole( accountRole.orElseThrow( () -> new NotFoundException( "Account Role not found." ) ) );
        account.setPassword( passwordEncoder.encode( account.getPassword() ) );
        Account savedAccount = accountRepository.save( account );
        logger.info( String.format( "Account created successfully saved. Account Id: %s", savedAccount.getId() ) );
    }

    @Override
    public Account updateAccount( long id, Account account ) {
        Optional<AccountRole> accountRole = getRoleByRoleName( account.getRole().getRoleName() );

        Account savedAccount = getAccountById( id ).get();
        savedAccount.setUsername( account.getUsername() );
        savedAccount.setRole( accountRole.orElseThrow( () -> new NotFoundException( "Account Role not found." ) ) );
        return accountRepository.save( savedAccount );
    }

    @Override
    public Page<Account> getAccounts( Pageable pageable ) {
        Page<Account> accounts = accountRepository.findAll( pageable );
        return new PageImpl<>( accounts.getContent(), pageable, accounts.getTotalElements() );
    }

    @Override
    public Optional<Account> getAccountById( long id ) {
        Optional<Account> account = Optional.ofNullable( accountRepository.findById( id )
                .orElseThrow( () -> new NotFoundException( String.format( "Account not found. Account id: %s", id ) ) ) );

        return account;
    }

    @Override
    public Optional<Account> getAccountByUsername( String username ) {
        return accountRepository.findByUsername( username );
    }

    @Override
    public void deleteAccount( long id ) {
        Optional<Account> account = getAccountById( id );
        accountRepository.delete( account.get() );
    }

    private Optional<AccountRole> getRoleByRoleName( String roleName ) {
        return roleService.getAccountRoleByRoleName( roleName );
    }

    @Override
    public AccountDto convertEntityToDTO( Account account ) {
        return modelMapper.map( account, AccountDto.class );
    }

    @Override
    public Account convertDtoToEntity( AccountDto accountDto ) {
        return modelMapper.map( accountDto, Account.class );

    }

    @Override
    public void changePassword( ChangePassword changePassword ) {
        if( changePassword.getId() <= 0 ) {
            throw new InvalidException( String.format(  "Invalid Account Id %s",changePassword.getId()  ) );
        }else if( ! changePassword.getPassword().equals( changePassword.getConfirmPassword() ) ) {
            throw new InvalidException( "Not match New Password. Please Try Again!" );
        }

        Optional<Account> account = getAccountById( changePassword.getId() );
        account.get().setPassword( passwordEncoder.encode( changePassword.getPassword() ) );
        accountRepository.save( account.get() );
    }

    private boolean comparePassword( Account account, String currentPassword ) {
        return passwordEncoder.matches( currentPassword, account.getPassword() );
    }

}
