package com.practise.grower.aspect;

import com.practise.grower.entity.Logger;
import com.practise.grower.entity.User;
import com.practise.grower.repository.LoggerRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Objects;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class LoggerAspect {

    private final LoggerRepo loggerRepo;

    @Before("execution(* com.practise.grower.controller.*.*.*(..))")
    public void beforeMethod(JoinPoint jp) {

        log.info("Executing method: " + jp.getSignature().getName());

       String methodName =  jp.getSignature().getName();

        String logMessage = "Method " + methodName + " is about to be executed.";

        String username = Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getName();


        Logger logger = new Logger();
        logger.setCreatedAt(LocalDateTime.now());
        logger.setUser(username);
        logger.setMessage(logMessage);

        loggerRepo.save(logger);

    }

    @Before("execution(* com.practise.grower.controller.*.*.*.*(..))")
    public void beforeMethodInPackage(JoinPoint jp) {

        log.info("Executing method: " + jp.getSignature().getName());

        String methodName =  jp.getSignature().getName();

        String logMessage = "Method " + methodName + " is about to be executed.";

        String username = Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getName();


        Logger logger = new Logger();
        logger.setCreatedAt(LocalDateTime.now());
        logger.setUser(username);
        logger.setMessage(logMessage);

        loggerRepo.save(logger);

    }

}
