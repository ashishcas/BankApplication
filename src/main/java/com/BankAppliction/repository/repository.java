package com.BankAppliction.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.BankAppliction.model.User;

public class repository {
	@Repository
	public interface UserRepository extends JpaRepository <User, String> {
		
		 User findByEmail (String email);
			
	}
    
}
