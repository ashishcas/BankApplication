package com.BankAppliction.service;

import com.BankAppliction.common.AccountMapper;
import com.BankAppliction.common.CreateBankAccountMapper;
import com.BankAppliction.model.BankAccount;

public interface AccountService {
    BankAccount createAccount(CreateBankAccountMapper account, String token) throws Exception;

    BankAccount updateAccount(BankAccount account);

    String deleteAccount(String token);
}