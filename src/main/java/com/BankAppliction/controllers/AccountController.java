package com.BankAppliction.controllers;

import com.BankAppliction.common.BalanceMapper;
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
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountServiceImpl accountServiceImpl;

    // add kafkTemplates
    private KafkaTemplate<String, String> kafkaTemplate;
    @Autowired
    private Gson gson;


    @Autowired
    public AccountController(KafkaTemplate<String, String> kafkaTemplate, Gson gson){
        this.kafkaTemplate = kafkaTemplate;
        this.gson = gson;
    }

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    private static final Logger logger= LoggerFactory.getLogger(Maincontroller.class);

    @PostMapping("/account")
    public ResponseEntity<BankAccount> createAccount(@RequestBody CreateBankAccountMapper accountData, @RequestHeader(value="access-token")  String token) throws Exception {

            BankAccount response = accountServiceImpl.createAccount(accountData, token);
            return new ResponseEntity(response, HttpStatus.OK);

    }

    /**
     *  endpoint to update the publish the data in kafka topic
     * */
    @PutMapping("/balance")
    public ResponseEntity<Optional> updatebalance(@RequestBody BalanceMapper balanceMapper){
//        kafkaTemplate.send("myTopic2", gson.toJson(balanceMapper));

        ListenableFuture<SendResult<String, String>> future =
      kafkaTemplate.send("myTopic2", gson.toJson(balanceMapper));

        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {

            @Override
            public void onSuccess(SendResult<String, String> result) {
                System.out.println("Sent data to topic with userid =[" + balanceMapper.getUserId() +
                        "] with offset=[" + result.getRecordMetadata().offset() + "]");
            }
            @Override
            public void onFailure(Throwable ex) {
                System.out.println("Unable data to topic with userid=["
                        + balanceMapper.getUserId() + "] due to : " + ex.getMessage());
            }
        });
        return new ResponseEntity("", HttpStatus.OK);
    }

    @KafkaListener(topics="myTopic2")
    public void readFromTopic(String dataSent){
        // TODO: add logic to update balance in db
        System.out.println("data read from topic"+ dataSent);
    }
}
