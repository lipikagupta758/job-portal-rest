package com.example.spring_boot_rest.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class PerformanceAspect {
    private static final Logger LOGGER= LoggerFactory.getLogger(PerformanceAspect.class);

    @Around("execution(* com.example.spring_boot_rest.service.JobService.*(..))")
    public Object monitorTime(ProceedingJoinPoint jp) throws Throwable {
        long start= System.currentTimeMillis();
        Object obj= jp.proceed();
        long end= System.currentTimeMillis();

        LOGGER.info("Time taken: "+ (end-start)+ " ms" + " by the method "+ jp.getSignature().getName());
        return obj;
    }
}
