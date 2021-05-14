package com.BankAppliction.exceptions;

/**
 * @author cashish
 *  Custom exception if the user email is not found in database
 * */
public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String message){
        super(message);
    }
}
