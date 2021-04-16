package com.BankAppliction.service;

import com.BankAppliction.model.User;
import org.bson.types.ObjectId;

import java.util.List;

public interface userService {

    List<User> getAll();

    List<User> getEmployeeByFirstName(String firstName);

    List<User> getEmployeeByEmail(String emailId);

    List<User> getEmployeeById(ObjectId _id);

}
