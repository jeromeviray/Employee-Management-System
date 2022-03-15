package com.project.account.controller;

import com.project.account.model.Account;
import com.project.account.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.project.common.contants.AppConstant.*;

@RestController
@RequestMapping(value = ENDPOINT_VERSION_ONE)
public class AccountController {

    @Autowired
    private AccountService accountService;

//    @PreAuthorize( "hasRole('SUPER_ADMIN')" )
    @RequestMapping(value = ACCOUNTS, method = RequestMethod.POST)
    public ResponseEntity<?> saveAccount(@Valid @RequestBody Account account){
        accountService.saveAccount( account );
        return new ResponseEntity<>( HttpStatus.CREATED );
    }

    @RequestMapping(value = LOGIN_ENDPOINT, method = RequestMethod.POST)
    public ResponseEntity<?> login(@RequestBody Account account){
        return new ResponseEntity<>( accountService.login( account.getUsername(), account.getPassword() ), HttpStatus.OK );
    }
}
