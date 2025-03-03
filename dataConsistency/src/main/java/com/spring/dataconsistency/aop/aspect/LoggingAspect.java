package com.spring.dataconsistency.aop.aspect;

import com.spring.dataconsistency.aop.annotation.Loggable;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    // 定义切点：拦截所有标记了 @Loggable 注解的方法
    @Pointcut("@annotation(com.spring.dataconsistency.aop.annotation.Loggable)")
    public void loggableMethods() {}

    // 定义环绕通知
    @Around("loggableMethods() && @annotation(loggable)")
    public Object logMethodExecution(ProceedingJoinPoint joinPoint, Loggable loggable) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        String customMessage = loggable.value();

        // 记录方法开始日志
        logger.info("{}: Method {} started with arguments: {}", customMessage, methodName, args);

        try {
            // 执行目标方法
            Object result = joinPoint.proceed();

            // 记录方法成功日志
            logger.info("{}: Method {} completed successfully", customMessage, methodName);
            return result;
        } catch (Exception e) {
            // 记录方法失败日志
            logger.error("{}: Method {} failed with exception: {}", customMessage, methodName, e.getMessage(), e);

            // 重新抛出异常
            throw e;
        }
    }
}
