package com.BankAppliction.controllers;

import com.BankAppliction.model.User;
import com.BankAppliction.repositories.repository;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
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
	repository userrepo;

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
		 userrepo.save(user) ;
		 System.out.println(user.getFirstName());
		 return new ResponseEntity(user, HttpStatus.CREATED);
	}
    
    
}
