package com.BankAppliction.controllers;

import com.BankAppliction.model.User;
import com.BankAppliction.repositories.UserRepository;

import com.BankAppliction.service.impl.UserServiceImpl;
import com.BankAppliction.utils.ErrorHandler;
import com.BankAppliction.utils.JwtTokenUtil;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.Gson;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

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
	private static final Logger logger=LoggerFactory.getLogger(Maincontroller.class);

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

	@PostMapping("/login")
	public ResponseEntity<User> loginUser( @RequestBody ObjectNode objectNode) {
		String emailId=objectNode.get("emailId").asText();
		String password=objectNode.get("password").asText();
		logger.info("emailId: {},password: {}",emailId,password);

		User newUser = userServiceImpl.loginUser(emailId,password) ;
		logger.info("newUser: {},password: {}",newUser,password);

		if(newUser.getEmailId().equals("UserExists")){
			ErrorHandler err = new ErrorHandler("400",HttpStatus.BAD_REQUEST,"user exists try different email","USER_EXISTS");
			err.getMessage();
			return new ResponseEntity(err, HttpStatus.BAD_REQUEST);
		}

		if(newUser.getEmailId().equals("InvalidEmail")){
			ErrorHandler err = new ErrorHandler("400",HttpStatus.BAD_REQUEST,"try valid email","INVALID_EMAIL");
			return new ResponseEntity(err, HttpStatus.BAD_REQUEST);
		}
		String accessToken = jwtTokenUtil.generateToken(newUser);
		HttpHeaders returnHeaders = new HttpHeaders();
		returnHeaders.add("accesstoken", accessToken);
		return new ResponseEntity(newUser,returnHeaders, HttpStatus.OK);
		}

		// route for testing token parsing
		@PostMapping("/tokenParser")
		public ResponseEntity<Claims> getClaims(@RequestHeader String access_token) throws Exception {
			Claims claims = jwtTokenUtil.validateToken(access_token);
			 if(claims == null){
				 return new ResponseEntity("token expired", HttpStatus.UNAUTHORIZED);
			 }
			Map<String, Object> jwtClaims = new HashMap<>(claims);
//			jwtClaims.forEach((key, value) -> System.out.println("[Key] : " + key + " [Value] : " + value));
			System.out.println(claims);
			logger.info("json: {},validToken: {}", jwtClaims.get("email"), claims);

			return new ResponseEntity(claims, HttpStatus.OK);
		}

}
