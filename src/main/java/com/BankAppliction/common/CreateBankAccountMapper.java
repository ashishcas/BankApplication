package com.BankAppliction.common;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class CreateBankAccountMapper {

    @NotNull
    private String aadharCard;

    //examples: 1900-01-01, 2205-02-31, 2999-12-31
    @NotNull
    @Pattern(regexp ="^((19|2[0-9])[0-9]{2})-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])", message="invalid of date of birth")
    private String dateOfBirth;


    @NotNull
    @Pattern(regexp ="\\\\d{10}|(?:\\\\d{3}-){2}\\\\d{4}|\\\\(\\\\d{3}\\\\)\\\\d{3}-?\\\\d{4}",message="invalid phone Number")
    private String phoneNumber;

    @NotNull
    @Pattern(regexp ="^[A-Za-z]{4}0[A-Z0-9a-z]{6}$",message="invalid IFSC code")
    private String ifscCode;

}
