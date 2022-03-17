package com.project.websecurity.impl;

import com.project.account.model.Account;
import com.project.account.service.AccountService;
import com.project.execption.NotFoundException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;


public class UserDetailsServiceImpl implements UserDetailsService {
    private AccountService accountService;

    public UserDetailsServiceImpl( AccountService accountService ) {
        this.accountService = accountService;
    }

    @Override
    public UserDetails loadUserByUsername( String username ) throws UsernameNotFoundException {
        Account account = accountService.getAccountByUsername( username ).orElseThrow(() -> new NotFoundException("Account not found") );

        return new User(
                account.getUsername(),
                account.getPassword(),
                true,
                true,
                true,
                true,
                getGrantedAuthorities( account ));
    }
    private List<GrantedAuthority> getGrantedAuthorities( Account account) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add( new SimpleGrantedAuthority( "ROLE_"+account.getRole().getRoleName() ) );
        return authorities;
    }
}
