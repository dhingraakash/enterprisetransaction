package com.enterprisetransaction.dto;

import com.enterprisetransaction.entity.Account;
import com.enterprisetransaction.entity.User;

public class EntityMapper {
    public static User toUser(UserDto dto) {
        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        return user;
    }

    public static Account toAccount(UserDto dto, User user) {
        Account acc = new Account();
        acc.setBalance(dto.getInitialDeposit());
        acc.setUser(user);
        acc.setAccountType(dto.getAccountType());
        return acc;
    }
}
