package com.project.websecurity.impl;

import com.project.account.model.Account;
import com.project.account.repository.AccountRepository;
import com.project.execption.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service(value = "userDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername( String username ) throws UsernameNotFoundException {
        Optional<Account> account = accountRepository.findByUsername( username );
        if(account.isEmpty()) throw new NotFoundException( String.format( "Account not found. Account Username: %s", username ) );

        return new User(
                account.get().getUsername(),
                account.get().getPassword(),
                true,
                true,
                true,
                true,
                getGrantedAuthorities( account.get() ));
    }
    private List<GrantedAuthority> getGrantedAuthorities( Account account) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add( new SimpleGrantedAuthority( "ROLE_"+account.getRole().getRoleName() ) );
        return authorities;
    }
}
