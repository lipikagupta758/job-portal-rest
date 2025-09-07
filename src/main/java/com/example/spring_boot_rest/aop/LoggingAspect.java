package com.example.spring_boot_rest.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect   //This is my Logging aspect class
public class LoggingAspect {
    private static final Logger LOGGER= LoggerFactory.getLogger(LoggingAspect.class);

    //The methods are the advice, JoinPoint is the service method object, type of advice is before, after, around and the parameters of @Before() is the point-cut is the expression which specifies where the advice should be implemented
    //return_type, class-name, method-name(args)
    @Before("execution(* com.example.spring_boot_rest.service.JobService.getJob(..))")
    public void logMethodCall(JoinPoint jp){
        LOGGER.info("Method called "+ jp.getSignature().getName());
    }
    //@After is after finally, it will get executed everytime
    @After("execution(* com.example.spring_boot_rest.service.JobService.getJob(..)) || execution(* com.example.spring_boot_rest.service.JobService.updateJob(..))")
    public void logMethodExecuted(JoinPoint jp){
        LOGGER.info("Method executed "+ jp.getSignature().getName());
    }

//    This will get executed if the method executes successfully
    @AfterReturning("execution(* com.example.spring_boot_rest.service.JobService.getJob(..))")
    public void logMethodSuccess(JoinPoint jp){
        LOGGER.info("Method executed successfully "+ jp.getSignature().getName());
    }

//    This will get executed if error is thrown in the function
    @AfterThrowing("execution(* com.example.spring_boot_rest.service.JobService.*(..))")
    public void logMethodCrash(JoinPoint jp){
        LOGGER.info("Method thrown an error "+ jp.getSignature().getName());
    }
}
