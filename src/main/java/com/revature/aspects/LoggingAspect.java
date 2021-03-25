package com.revature.aspects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {

    private final Logger logger =LogManager.getLogger(LoggingAspect.class);


    @Pointcut("within(com.revature..*) && !within(com.revature.filters..*)")
    public void logAllPointcut() {
        logger.info("!!!!!!!");
    }


    @Before("logAllPointcut()")
    public void logMethodStart(JoinPoint jp) {
        String methodSig = extractMethodSignature(jp);
        String argStr = Arrays.toString(jp.getArgs());
        logger.info("1<(^.^)> {} invoked at {}; input arguments: {}", methodSig, LocalDateTime.now(), argStr);
    }

    private String extractMethodSignature(JoinPoint jp){
        return jp.getTarget().getClass().toString() + "." + jp.getSignature().getName();
    }

    @AfterReturning(pointcut = "logAllPointcut()", returning = "returned")
    public void logMethodReturned(JoinPoint jp, Object returned) {
        String methodSig = extractMethodSignature(jp);
        logger.info("2<(^.^)> {} was successfully returned at {} with a value of {}", methodSig, LocalDateTime.now(), returned);
    }

    @AfterThrowing(pointcut = "logAllPointcut()", throwing = "e")
    public void logErrorOccurrence(JoinPoint jp, Exception e) {
        String methodSig = extractMethodSignature(jp);
        logger.error("3<(^.^)> {} was thrown in method {} at {} with message: {}", e.getClass().getSimpleName(), methodSig, LocalDateTime.now(), e.getMessage());
    }
}

