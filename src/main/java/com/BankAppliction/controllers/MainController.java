package com.BankAppliction.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.BankAppliction.service.Service;

@RestController
public class MainController {
	@Autowired
	Service service;

    @GetMapping(value="/health")
    public String healthCheck(){
        return "server is running";
    }
	@GetMapping("/login")
	public String login(@RequestParam (required = false) String email, @RequestParam (required = false) String password) {
		
		return service.findbyEmail(email, password);

	}

	@GetMapping("/newUser")
	public String addUser(@RequestParam (required = false) String firstName,@RequestParam (required = false) String lastName, @RequestParam (required = false) String password, @RequestParam (required = false) String email,
			@RequestParam boolean isAdmin) {
		return service.addUser(firstName,lastName, password, email) ;

	}
    
    
}
