package com.BankAppliction.service.impl;

import com.BankAppliction.model.User;
import com.BankAppliction.service.userService;
import org.bson.types.ObjectId;

import java.util.List;

public class userServiceImpl implements userService {
    @Override
    public List<User> getAll() {
        return null;
    }

    @Override
    public List<User> getEmployeeByFirstName(String firstName) {
        return null;
    }

    @Override
    public List<User> getEmployeeByEmail(String emailId) {
        return null;
    }

    @Override
    public List<User> getEmployeeById(ObjectId _id) {
        return null;
    }
}
