package com.project.account.controller;

import com.project.account.model.Account;
import com.project.account.model.AccountDto;
import com.project.account.model.ChangePassword;
import com.project.account.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.project.common.contants.AppConstant.*;

@RestController
@RequestMapping( value = ENDPOINT_VERSION_ONE )
public class AccountController {

    @Autowired
    private AccountService accountService;

    @RequestMapping( value = ACCOUNTS, method = RequestMethod.POST )
    public ResponseEntity<?> saveAccount( @Valid @RequestBody Account account ) {
        accountService.saveAccount( account );
        return new ResponseEntity<>( HttpStatus.CREATED );
    }

    @PreAuthorize( "hasRole('ROLE_ADMIN')" )
    @RequestMapping( value = ACCOUNTS + PATH_VARIABLE_ID, method = RequestMethod.PUT )
    public ResponseEntity<?> updateAccount( @PathVariable int id,@Valid @RequestBody Account account ) {
        Account updatedAccount = accountService.updateAccount( id, account );
        return new ResponseEntity<>( accountService.convertEntityToDTO( updatedAccount ), HttpStatus.OK );
    }

    @PreAuthorize( "hasRole('ROLE_ADMIN')" )
    @RequestMapping( value = ACCOUNTS, method = RequestMethod.GET )
    public ResponseEntity<?> getAccounts( @RequestParam( value = "page", defaultValue = DEFAULT_PAGE_NUMBER, required = false ) int page,
                                          @RequestParam( value = "limit", defaultValue = DEFAULT_PAGE_SIZE, required = false ) int limit,
                                          @RequestParam( value = "sortBy", defaultValue = DEFAULT_SORT_BY, required = false ) String sortBy,
                                          @RequestParam( value = "orderBy", defaultValue = DEFAULT_ORDER_BY, required = false ) String orderBy ) {
        Sort sort = orderBy.equalsIgnoreCase( Sort.Direction.ASC.name() ) ? Sort.by( sortBy ).ascending() : Sort.by( sortBy ).descending();
        Pageable pageable = PageRequest.of( page, limit, sort );
        Page<Account> accountResults = accountService.getAccounts( pageable );
        List<AccountDto> accounts = accountResults.stream().map( accountService :: convertEntityToDTO ).collect( Collectors.toList() );
        Map<String, Object> response = new HashMap<>();
        response.put( "data", accounts );
        response.put( "currentPage", accountResults.getNumber() );
        response.put( "totalPages", accountResults.getTotalPages() );

        return new ResponseEntity<>( response, HttpStatus.OK );
    }

    @PreAuthorize( "hasRole('ROLE_ADMIN')" )
    @RequestMapping( value = ACCOUNTS + PATH_VARIABLE_ID, method = RequestMethod.DELETE )
    public ResponseEntity<?> deleteAccount( @PathVariable long id ) {
        accountService.deleteAccount( id );
        return new ResponseEntity<>( HttpStatus.OK );
    }

    @PreAuthorize( "hasRole('ROLE_ADMIN')" )
    @RequestMapping( value = ACCOUNTS + PATH_VARIABLE_USERNAME, method = RequestMethod.GET )
    public ResponseEntity<?> getAccountByUsername( @PathVariable( "username" ) String username ) {
        Account account = accountService.getAccountByUsername( username ).get();
        return new ResponseEntity<>( accountService.convertEntityToDTO( account ), HttpStatus.OK );
    }

    @PreAuthorize( "hasRole('ROLE_ADMIN')" )
    @RequestMapping( value = ACCOUNTS+CHANGE_ENDPOINT+PASSWORD_ENDPOINT, method = RequestMethod.PUT )
    public ResponseEntity<?> changePassword(  @Valid @RequestBody ChangePassword changePassword ) {
        accountService.changePassword(  changePassword );
        return new ResponseEntity<>( HttpStatus.OK );
    }
}
