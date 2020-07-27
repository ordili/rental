package com.baocheng.rental.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Aspect
@Component
public class LogAspect {

    private Logger log = LoggerFactory.getLogger(LogAspect.class);

    ThreadLocal<Long> startTime = new ThreadLocal<>();

    /**
     * point
     */
    @Pointcut("execution(public * com.baocheng.rental.controller.RentalCarController.*(..))")
    public void requestLog() {
    }

    ;

    /**
     * @param joinPoint joinPoint
     */
    @Before("requestLog()")
    public void doBefore(JoinPoint joinPoint) {
        startTime.set(System.currentTimeMillis());
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();

        log.info("RequestMapping:[{}]", request.getRequestURI());
        log.info("RequestParam:{}", Arrays.toString(joinPoint.getArgs()));
    }

    /**
     * @param response
     */
    @AfterReturning(returning = "response", pointcut = "requestLog()")
    public void doAfterRunning(Object response) {

        log.info("Response:[{}]", response);
        log.info("Request spend times : [{}ms]", System.currentTimeMillis() - startTime.get());
    }
}
