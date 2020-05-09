package com.pwj.upload.aop;

import com.pwj.upload.aop.annotation.ChangeSource;
import com.pwj.upload.util.DynamicThreadLocalHolder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
@Slf4j
public class ChangeSourceAspect {

    @Pointcut("@annotation(com.pwj.upload.aop.annotation.ChangeSource)")
    public void pointcut() {
    }
    /**
     * 在方法执行之前往ThreadLocal中设置值
     */
    @Before(value = "pointcut()")
    public void beforeOpt(JoinPoint joinPoint) {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        ChangeSource changeSource = method.getAnnotation(ChangeSource.class);
        log.info("[Switch DataSource]:" + changeSource.value());
        DynamicThreadLocalHolder.setDataSource(changeSource.value());
    }
    /**
     * 结束之后清除
     */
    @After(value = "pointcut()")
    public void afterOpt() {
        DynamicThreadLocalHolder.clearDataSource();
        log.info("[change Default DataSource]");
    }

}
