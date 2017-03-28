package org.jsche.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.jsche.common.util.AppUtil;
import org.jsche.entity.SystemUsage;
import org.jsche.repo.SystemUsageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
@Aspect
public class SystemAccessAspect {
    private Logger logger = LoggerFactory.getLogger(getClass());
    private SystemUsage usage;

    @Autowired
    private SystemUsageRepository sup;

    @Pointcut("execution(public * org.jsche.controller..*.*(..))")
    public void logPoint() {

    }

    @Before("logPoint()")
    public void doBefore(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest req = attributes.getRequest();
        logger.info("**System access log**");
        usage = new SystemUsage();
        usage.setDateStamp(new Date(System.currentTimeMillis()));
        usage.setClientIp(AppUtil.getClienIp(req));
        usage.setMethod(req.getMethod());
        usage.setPath(req.getRequestURI());
    }

    @AfterReturning(returning = "r", pointcut = "logPoint()")
    public void afterReturning(Object r) {
        usage.setStatus(200);
        sup.save(usage);
        logger.info(r.toString());
    }

    @AfterThrowing(throwing = "e", pointcut = "logPoint()")
    public void afterThrowing(Throwable e) {
        usage.setStatus(500);
        sup.save(usage);
        logger.error(e.getMessage());
    }


}
