package com.BankAppliction.controllers;

import com.BankAppliction.model.User;
import com.BankAppliction.repositories.UserRepository;

import com.BankAppliction.service.impl.UserServiceImpl;
import com.BankAppliction.utils.ErrorHandler;
import com.BankAppliction.utils.JwtTokenUtil;
import com.google.gson.Gson;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Maincontroller {

	@Autowired
	private UserRepository userrepo;

	@Autowired
	private UserServiceImpl userServiceImpl;

	@Autowired
	private Gson gson;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;


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

		String accessToken = jwtTokenUtil.generateToken(user);
		HttpHeaders returnHeaders = new HttpHeaders();
		returnHeaders.add("accesstoken", accessToken);
		return new ResponseEntity(user,returnHeaders, HttpStatus.CREATED);
	}
    
    
}
