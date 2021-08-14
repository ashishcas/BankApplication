package com.BankAppliction.service.impl;

import com.BankAppliction.common.AccountMapper;
import com.BankAppliction.common.CreateBankAccountMapper;
import com.BankAppliction.exceptions.ResourceAlreadyExistsException;
import com.BankAppliction.exceptions.UnauthorizedException;
import com.BankAppliction.model.BankAccount;
import com.BankAppliction.model.User;
import com.BankAppliction.repositories.AccountRepository;
import com.BankAppliction.service.AccountService;
import com.BankAppliction.utils.JwtTokenUtil;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import io.jsonwebtoken.Claims;

import java.util.Base64;
import java.util.List;
import java.util.Random;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;

import org.bson.types.ObjectId;
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

    @Autowired
    private Gson gson;

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
    public BankAccount createAccount(CreateBankAccountMapper account, String token) throws Exception {

        Claims claims;

//        try {
            claims = jwtTokenUtil.validateToken(token);

            String id = claims.get("id",String.class);
            BankAccount accountExists = getAccountByUserId(id);
            if(accountExists == null){
                Random r = new Random();
                long accountNumber = r.nextInt(1_000_000_000)               // Last 9 digits
                        + (r.nextInt(90) + 10) * 1_000_000_000L;
                String  accountData = gson.toJson(account,CreateBankAccountMapper.class);
                JsonElement jsonElement = gson.fromJson(accountData, JsonElement.class);
//                        gson.toJsonTree(accountData);

                jsonElement.getAsJsonObject().addProperty("_id", ObjectId.get().toString());
                jsonElement.getAsJsonObject().addProperty("accountNumber",Long.toString(accountNumber));
                jsonElement.getAsJsonObject().addProperty("userId",id);
                jsonElement.getAsJsonObject().addProperty("currentBalance",0);
                accountData = gson.toJson(jsonElement);

                BankAccount bankaccount = gson.fromJson(accountData, BankAccount.class);
                return accountRepository.save(bankaccount);
            } else {
                // throw an exception account already exists
                BankAccount response = null;
                throw new ResourceAlreadyExistsException("Bank account already exists");

            }
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
