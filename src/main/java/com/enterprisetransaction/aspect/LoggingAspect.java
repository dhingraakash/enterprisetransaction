package com.enterprisetransaction.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Around("@annotation(com.enterprisetransaction.anotations.SaveTransaction)")
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info("Starting fund transfer...");
        try {
            Object result = joinPoint.proceed();
            logger.info("Fund transfer completed.");
            return result;
        } catch (Throwable ex) {
            logger.error("Fund transfer failed: " + ex.getMessage());
            throw ex;
        }
    }
}
