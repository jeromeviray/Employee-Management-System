package com.project.account.service;

import com.project.account.model.Account;
import com.project.account.model.AccountDto;
import com.project.account.model.ChangePassword;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface AccountService {

    void saveAccount( Account account );

    Account updateAccount( long id, Account account );

    Page<Account> getAccounts( Pageable pageable );

    Optional<Account> getAccountById( long id );

    Optional<Account> getAccountByUsername( String username );

    void deleteAccount( long id );

    AccountDto convertEntityToDTO(Account account);

    Account convertDtoToEntity(AccountDto accountDto);

    void changePassword(ChangePassword changePassword);
}
