package com.BankAppliction.controllers;

import com.BankAppliction.common.CreateBankAccountMapper;
import com.BankAppliction.exceptions.ResourceAlreadyExistsException;
import com.BankAppliction.model.BankAccount;
import com.BankAppliction.model.User;
import com.BankAppliction.repositories.AccountRepository;
import com.BankAppliction.repositories.UserRepository;
import com.BankAppliction.service.impl.AccountServiceImpl;
import com.BankAppliction.service.impl.UserServiceImpl;
import com.BankAppliction.utils.ErrorHandler;
import com.BankAppliction.utils.JwtTokenUtil;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountServiceImpl accountServiceImpl;


    @Autowired
    private Gson gson;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    private static final Logger logger= LoggerFactory.getLogger(Maincontroller.class);

    @PostMapping("/account")
    public ResponseEntity<BankAccount> createAccount(@RequestBody CreateBankAccountMapper accountData, @RequestHeader(value="access-token")  String token){

            BankAccount response = accountServiceImpl.createAccount(accountData, token);
            if(response == null){
                // throw an exception account already exists
                throw new ResourceAlreadyExistsException("Bank account already exists");
            }
            return new ResponseEntity(response, HttpStatus.OK);

//        catch(ResourceAlreadyExistsException exp){
//            ErrorHandler err = new ErrorHandler("400",HttpStatus.BAD_REQUEST,"bank account already exists with this id","ACCOUNT_EXISTS");
//            err.getMessage();
//            return new ResponseEntity(err, HttpStatus.BAD_REQUEST);
//        }

    }
}
