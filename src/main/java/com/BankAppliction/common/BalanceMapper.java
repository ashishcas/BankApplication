package com.BankAppliction.common;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class BalanceMapper {
    @NotNull
    private String userId;

    @NotNull
    private String type;

    @NotNull
    private String amount;
}
