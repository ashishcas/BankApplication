package com.BankAppliction.utils;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;

@Getter
@Setter
public class ErrorHandler {

    private String status;
    private  HttpStatus statusCode;
    private String message;
    private List<String> errors;

    public ErrorHandler(String status,HttpStatus statusCode, String message, List<String> errors) {
        super();
        this.statusCode = statusCode;
        this.status = status;
        this.message = message;
        this.errors = errors;
    }

    public ErrorHandler(String status,HttpStatus statusCode, String message, String error) {
        super();
        this.status = status;
        this.statusCode=statusCode;
        this.message = message;
        errors = Arrays.asList(error);
    }

}
