package com.enterprisetransaction.dto;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UserDto {
    @NotBlank
    private String name;
    @Email
    private String email;
    @NotBlank
    private String accountType;
    private Double initialDeposit;

    public UserDto() {
    }

    public UserDto(String name, String email, String accountType, Double initialDeposit) {
        this.name = name;
        this.email = email;
        this.accountType = accountType;
        this.initialDeposit = initialDeposit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public Double getInitialDeposit() {
        return initialDeposit;
    }

    public void setInitialDeposit(Double initialDeposit) {
        this.initialDeposit = initialDeposit;
    }
}
