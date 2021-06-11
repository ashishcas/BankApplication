package com.BankAppliction.model;

import lombok.*;
import org.bson.types.ObjectId;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@TypeAlias("account")
@Document(collection ="account")
public class BankAccount {

    @Id
    @Generated
    private String _id;

    @Pattern(regexp="^\\d{11}", message="please give valid accountNumber")
    @NotNull
    private String accountNumber;

    @NotNull
    private String aadharCard;

    @NotNull
    private String userId;

    //examples: 1900-01-01, 2205-02-31, 2999-12-31
    @NotNull
    @Pattern(regexp ="^((19|2[0-9])[0-9]{2})-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])", message="invalid of date of birth")
    private String dateOfBirth;

    @NotNull
    @Min(value=0, message="the minimum balance is zero")
    private Integer currentBalance;

    @NotNull
    @Pattern(regexp ="\\\\d{10}|(?:\\\\d{3}-){2}\\\\d{4}|\\\\(\\\\d{3}\\\\)\\\\d{3}-?\\\\d{4}",message="invalid phone Number")
    private String phoneNumber;

    @NotNull
    @Pattern(regexp ="^[A-Za-z]{4}0[A-Z0-9a-z]{6}$",message="invalid IFSC code")
    private String ifscCode;
}
