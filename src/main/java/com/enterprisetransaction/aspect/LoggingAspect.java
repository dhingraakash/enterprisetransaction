package com.enterprisetransaction.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
    @Around("@annotation(com.enterprisetransaction.aspect.SaveTransaction)")
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("🔁 Starting fund transfer...");
        try {
            Object result = joinPoint.proceed();
            System.out.println("✅ Fund transfer completed.");
            return result;
        } catch (Throwable ex) {
            System.out.println("❌ Fund transfer failed: " + ex.getMessage());
            throw ex;
        }
    }
}
