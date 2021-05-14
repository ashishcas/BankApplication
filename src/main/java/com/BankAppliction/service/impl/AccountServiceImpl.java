package com.BankAppliction.service.impl;

import com.BankAppliction.common.AccountMapper;
import com.BankAppliction.exceptions.UnauthorizedException;
import com.BankAppliction.model.BankAccount;
import com.BankAppliction.repositories.AccountRepository;
import com.BankAppliction.service.AccountService;
import com.BankAppliction.utils.JwtTokenUtil;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private AccountRepository repository;

    private static final Logger logger= LoggerFactory.getLogger(AccountServiceImpl.class);


    @Override
    public AccountMapper createAccount(BankAccount account, String token) {

        Claims claims;

        try {
            claims = jwtTokenUtil.validateToken(token);
        }catch(Exception exp){
            exp.printStackTrace();
            logger.error("authorization failed");

        }

        return null;
    }

    @Override
    public BankAccount updateAccount(BankAccount account) {
        return null;
    }

    @Override
    public String deleteAccount(String token) {
        return null;
    }
}
