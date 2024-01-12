package com.example.bankspringsecurity.dto;

import lombok.Builder;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public record AccountDepositRequest(
        @NotNull
        @Digits(integer = 4, fraction = 4)
        Long number,

        @NotNull
        Long amount,

        @NotEmpty
        @Pattern(regexp = "^(DEPOSIT)$")
        String type,

        @NotEmpty
        @Pattern(regexp = "^[0-9]{11}")
        String phoneNumber
) {

}
