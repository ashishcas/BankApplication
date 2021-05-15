package com.BankAppliction.service;

import com.BankAppliction.common.AccountMapper;
import com.BankAppliction.model.BankAccount;

public interface AccountService {
    BankAccount createAccount(BankAccount account,String token);

    BankAccount updateAccount(BankAccount account);

    String deleteAccount(String token);
}