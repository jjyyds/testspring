package com.yc.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(100)
public class LogAspect2 {

    @Around("execution(* com.yc.biz.StudentBizImpl.update*(..))")
    public Object compute2(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("compute2进入");
        long start=System.currentTimeMillis();
        Object reVal=pjp.proceed();
        long end =System.currentTimeMillis();
        System.out.println("2方法执行时间为:"+(end-start));
        return reVal;
    }
}
