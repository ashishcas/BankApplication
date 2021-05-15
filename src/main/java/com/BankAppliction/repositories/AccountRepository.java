package com.BankAppliction.repositories;

import com.BankAppliction.model.BankAccount;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends MongoRepository<BankAccount, String> {
    List<BankAccount> findAll();
}
