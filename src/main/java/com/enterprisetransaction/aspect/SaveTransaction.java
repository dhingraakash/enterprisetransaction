package com.enterprisetransaction.aspect;

import com.enterprisetransaction.enums.TransactionType;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SaveTransaction {
    TransactionType type();
}
