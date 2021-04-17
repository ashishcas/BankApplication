package com.BankAppliction.controllers;

import com.BankAppliction.model.User;
import com.BankAppliction.repositories.repository;

import com.BankAppliction.service.impl.userServiceImpl;
import com.BankAppliction.utils.ErrorHandler;
import com.google.gson.Gson;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
public class Maincontroller {

	@Autowired
	repository userrepo;

	@Autowired
	userServiceImpl userServiceImpl;

	@Autowired
	private Gson gson;

    @GetMapping(value="/health")
    public String healthCheck(){
		User newuser = new User();
		
        return "server is running";
    }
	// @GetMapping("/login")
	// public String login(@RequestParam (required = false) String email, @RequestParam (required = false) String password) {
		
	// 	return service.checkEmail(email, password);

	// }

	@PostMapping("/newUser")
	public ResponseEntity<User> addUser(@Validated @RequestBody User user ) {
		user.set_id(ObjectId.get());
		User newUser = userServiceImpl.createUser(user) ;

		if(newUser.getEmailId().equals("UserExists")){
			ErrorHandler err = new ErrorHandler("400",HttpStatus.BAD_REQUEST,"user exists try different email","USER_EXISTS");
			err.getMessage();
			return new ResponseEntity(err, HttpStatus.BAD_REQUEST);
		}

		if(newUser.getEmailId().equals("InvalidEmail")){
			ErrorHandler err = new ErrorHandler("400",HttpStatus.BAD_REQUEST,"try valid email","INVALID_EMAIL");
			return new ResponseEntity(err, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity(user, HttpStatus.CREATED);
	}
    
    
}
