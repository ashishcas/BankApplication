package com.BankAppliction.service.impl;

import com.BankAppliction.common.AccountMapper;
import com.BankAppliction.exceptions.UnauthorizedException;
import com.BankAppliction.model.BankAccount;
import com.BankAppliction.model.User;
import com.BankAppliction.repositories.AccountRepository;
import com.BankAppliction.service.AccountService;
import com.BankAppliction.utils.JwtTokenUtil;
import io.jsonwebtoken.Claims;

import java.util.Base64;
import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    MongoTemplate mongoTemplate;
    @Autowired
    private AccountRepository accountRepository;

    private static final Logger logger= LoggerFactory.getLogger(AccountServiceImpl.class);

    public BankAccount getAccountByUserId(String userId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("userId").is(userId));
        List<BankAccount> accountList = mongoTemplate.find(query, BankAccount.class);

        for(BankAccount account: accountList ){
            if(account.getUserId().equals(userId)){
                return account;
            }
        }

        BankAccount accountObj = null ;
        return accountObj;
    }
    
    
    @Override
    public BankAccount createAccount(BankAccount account, String token) {

        Claims claims;

        try {
            claims = jwtTokenUtil.validateToken(token);
            
            BankAccount accountExists = getAccountByUserId(account.getUserId());
            if(accountExists.get_id().equals("NotFound")){

                return accountRepository.save(account);
            }
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
