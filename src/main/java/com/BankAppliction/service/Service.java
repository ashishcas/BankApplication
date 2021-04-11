package com.BankAppliction.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.BankAppliction.model.User;
import com.BankAppliction.repository.repository.UserRepository;

public class Service {
	@Autowired
	UserRepository userRepository;
	User user;
	public String findbyEmail(String email,String password) {
		
		
		user = userRepository.findByEmail(email);
		if(user != null) {
		
			if(user.getPassword().equals(password)) {
				// TODO REMOVE THIS WHEN UI IS DONE
				return "<h1>SUCCESS</h1>";
			}
			else {
				// TODO REMOVE THIS WHEN UI IS DONE
				return "<h1>WRONG PASSWORD</h1>";
			}
				
		}
		else {
			// TODO REMOVE THIS WHEN UI IS DONE
			return "<h1>USER NOT PRESENT</h1>" ;
		}
		 
	}
	public String addUser(String firstName, String lastName, String password, String email) {
		// TODO Auto-generated method stub
		user =  userRepository.findByEmail(email);
			
		if(user == null){
			user = userRepository.findByEmail(email);
			if(user == null) {
			User user = new  User (firstName,lastName, email, password);
			// TODO REMOVE THIS WHEN UI IS DONE
			return "<h1>USER ADDED</h1>";
			}
			else
			{
				// TODO REMOVE THIS WHEN UI IS DONE
				return "<h1>EMAIL ALREADY EXISTS</h1>";
			}
		}
		else {
			// TODO REMOVE THIS WHEN UI IS DONE
			return "<h1>USER ALREADY PRESENT</h1>";
		}
	}
    
}
