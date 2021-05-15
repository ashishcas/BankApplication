package com.BankAppliction.service;

import com.BankAppliction.model.User;
import org.bson.types.ObjectId;

import java.util.List;

/**
 * @author ashish
 * service for handling bank account
 * */
public interface UserService {

    List<User> getAll();

    List<User> getEmployeeByFirstName(String firstName);

    User getEmployeeByEmail(String emailId);

    List<User> getEmployeeById(ObjectId _id);

    User createUser(User user);


}
