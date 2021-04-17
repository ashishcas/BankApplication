package com.BankAppliction.service.impl;

import com.BankAppliction.model.User;
import com.BankAppliction.repositories.repository;
import com.BankAppliction.service.userService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class userServiceImpl implements userService {

    @Autowired
    repository userRepository;

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public List<User> getEmployeeByFirstName(String firstName) {
        return null;
    }

    @Override
    public User getEmployeeByEmail(String emailId) {

        List<User> userList = userRepository.findAll();

        for(User user: userList ){
            if(user.getEmailId().equals(emailId)){
                return user;
            }
        }

        return  User.builder().firstName("NotFound").secondName("NotFound").password("NotFound").emailId("NotFound").build();
    }

    @Override
    public List<User> getEmployeeById(ObjectId _id) {
        return null;
    }

    @Override
    public User createUser(User user) {

        // Check for valid email using regex
        String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(user.getEmailId());

        if(!matcher.matches()){
            return  User.builder().firstName("InvalidEmail").secondName("UserExists").emailId("InvalidEmail").password("InvalidEmail").build();
        }

        User userExists = getEmployeeByEmail(user.getEmailId());
        if(userExists.getEmailId().equals("NotFound")){
            return userRepository.save(user);
        } else {
            return  User.builder().firstName("UserExists").secondName("UserExists").password("UserExists").emailId("UserExists").build();
        }
    }
}
