package com.project.account.service;

import com.project.account.model.Account;
import com.project.websecurity.jwtUtil.response.JwtResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AccountService {

    void saveAccount( Account account);
    Account updateAccount(long id, Account account);
    Page<Account> getAccounts( String query, Pageable pageable );
    Account getAccountById(long id);
    Account getAccountByUsername(String username);
    JwtResponse login(String username, String password);
}
