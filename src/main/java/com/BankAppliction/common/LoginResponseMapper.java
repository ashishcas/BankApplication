package com.BankAppliction.common;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;

@Getter
@Setter
public class LoginResponseMapper {
    private ObjectId _id;
    private String firstName;
    private String secondName;
    private String emailId;
    private String password;
    private String access_token;
}
