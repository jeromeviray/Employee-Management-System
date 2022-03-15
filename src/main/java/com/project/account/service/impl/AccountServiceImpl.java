package com.project.account.service.impl;

import com.project.account.model.Account;
import com.project.account.model.AccountRole;
import com.project.account.repository.AccountRepository;
import com.project.account.service.AccountService;
import com.project.account.service.RoleService;
import com.project.execption.AuthenticatingCredentialsException;
import com.project.execption.NotFoundException;
import com.project.websecurity.jwtUtil.provider.JwtProvider;
import com.project.websecurity.jwtUtil.response.JwtResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {
    Logger logger = LoggerFactory.getLogger( AccountServiceImpl.class );

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtProvider provider;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public JwtResponse login( String username, String password ) {
        try{
            authenticationManager.authenticate( new UsernamePasswordAuthenticationToken( username, password ) );
        }catch( BadCredentialsException badCredentialsException ){
            throw new AuthenticatingCredentialsException("INVALID_CREDENTIALS: " + badCredentialsException);
        }
        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        final String token = provider.doGenerateAccessToken(userDetails.getUsername());
        Account account = getAccountByUsername( userDetails.getUsername() );
        return new JwtResponse(account.getUsername(), token, account.getRole());
    }

    @Override
    public void saveAccount( Account account ) {
        AccountRole accountRole = getRoleByRoleName( account.getRole().getRoleName() );
        account.setRole( accountRole );
        account.setPassword( passwordEncoder.encode( account.getPassword() ) );
        Account savedAccount = accountRepository.save( account );
        logger.info( String.format( "Account created successfully saved. Account Id: %s", savedAccount.getId() ) );
    }

    @Override
    public Account updateAccount( long id, Account account ) {
        AccountRole accountRole = getRoleByRoleName( account.getRole().getRoleName() );

        Account savedAccount = getAccountById( id );
        savedAccount.setUsername( account.getUsername() );
        savedAccount.setRole( accountRole );
        return accountRepository.save( savedAccount );
    }

    @Override
    public Page<Account> getAccounts( String query, Pageable pageable ) {
        return null;
    }

    @Override
    public Account getAccountById( long id ) {
        Optional<Account> account = accountRepository.findById( id );
        if( account.isEmpty() ) throw new NotFoundException( String.format( "Account not found. Account ID: %s", id ) );

        return account.get();
    }

    @Override
    public Account getAccountByUsername( String username ) {
        Optional<Account> account = accountRepository.findByUsername( username );
        if(account.isEmpty()) throw new NotFoundException(String.format( "Account not found. Account username: %s", username ));
        return account.get();
    }

    private AccountRole getRoleByRoleName( String roleName){
        return roleService.getAccountRoleByRoleName( roleName );
    }
}
