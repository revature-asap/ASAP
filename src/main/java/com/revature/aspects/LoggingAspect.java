package com.revature.aspects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * Logging Aspect -- Sets logging to run all methods in all classes (except on filters)
 * runs before method is called & after method finishes (successfully or not)
 */
@Aspect
@Component
public class LoggingAspect {

    private final Logger logger = LogManager.getLogger(LoggingAspect.class);

    /**
     * Sets pointcut to all classes (except filters)
     */
    @Pointcut("within(com.revature..*) && !within(com.revature.filters..*)")
    public void logAllPointcut() {}

    /**
     * Provides a LOG message to the console immediately preceding a method
     * @param jp join point
     */
    @Before("logAllPointcut()")
    public void logMethodStart(JoinPoint jp) {
        String methodSig = extractMethodSignature(jp);
        String argStr = Arrays.toString(jp.getArgs());
        logger.info("{} invoked at {}; input arguments: {}", methodSig, LocalDateTime.now(), argStr);
    }

    /**
     * Generates the fully qualified method name
     * @param jp join point
     * @return fully qualified method name
     */
    private String extractMethodSignature(JoinPoint jp){
        return jp.getTarget().getClass().toString() + "." + jp.getSignature().getName();
    }

    /**
     * Provides a LOG message to the console when a method returns/finishes
     * @param jp join point
     * @param returned fully qualified method name, time
     */
    @AfterReturning(pointcut = "logAllPointcut()", returning = "returned")
    public void logMethodReturned(JoinPoint jp, Object returned) {
        String methodSig = extractMethodSignature(jp);
        logger.info("{} was successfully returned at {} with a value of {}", methodSig, LocalDateTime.now(), returned);
    }

    /**
     * Provides a LOG message to the console when a method throws an exception
     * @param jp join point
     * @param e exception thrown by the method
     */
    @AfterThrowing(pointcut = "logAllPointcut()", throwing = "e")
    public void logErrorOccurrence(JoinPoint jp, Exception e) {
        String methodSig = extractMethodSignature(jp);
        logger.error("{} was thrown in method {} at {} with message: {}", e.getClass().getSimpleName(), methodSig, LocalDateTime.now(), e.getMessage());
    }
}

