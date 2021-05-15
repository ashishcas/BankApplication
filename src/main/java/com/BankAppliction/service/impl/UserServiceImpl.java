package com.BankAppliction.service.impl;

import com.BankAppliction.controllers.Maincontroller;
import com.BankAppliction.exceptions.ResourceNotFoundException;
import com.BankAppliction.model.User;
import com.BankAppliction.repositories.UserRepository;
import com.BankAppliction.service.UserService;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements UserService {


	private static final Logger logger=LoggerFactory.getLogger(UserService.class);

    @Autowired
    UserRepository userRepository;

    @Autowired
    MongoTemplate mongoTemplate;

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
        Query query = new Query();
        query.addCriteria(Criteria.where("emailId").is(emailId));
        List<User> userList = mongoTemplate.find(query, User.class);

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
            String encodedString = Base64.getEncoder().encodeToString(user.getPassword().getBytes());
            user.setPassword(encodedString);
            return userRepository.save(user);
        }

        return  User.builder().firstName("UserExists").secondName("UserExists").password("UserExists").emailId("UserExists").build();
        
    }

    /**
     *
     * */
    public User loginUser(String email, String password) throws ResourceNotFoundException {

        // Check for valid email using regex
        String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);

        Query query = new Query();
        query.addCriteria(Criteria.where("emailId").is(email));
        List<User> users = null;
        users = mongoTemplate.find(query, User.class);
        if(users.size() == 0){
            User userObj = null;
            return userObj;
        }
        for (User other : users) {
            System.out.println(other);
            String encodedString = Base64.getEncoder().encodeToString(password.getBytes());
            logger.info("entered password:{}, encryptedPassword:{}, getPassword:{}",password,encodedString,other.getPassword());
            if (encodedString.equals(other.getPassword())) {
            	System.out.println("Inside If"+ other);
                return other;
            }
        
        }
        return  User.builder().firstName("UserExists").secondName("UserExists").password("UserExists").emailId("UserExists").build();

    }
}
