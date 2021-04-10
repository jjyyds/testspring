package com.yc.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

//切面类：要增强的功能
@Aspect
@Component
public class LogAspect {

    @Pointcut("execution(* com.yc.biz.StudentBizImpl.add*(..))")
    private native void method();

    @Before("method()")
    public void beforeAdd(){
        System.err.println("前置增强");
        Date d=new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str=sdf.format(d);
        System.err.println("执行时间为: "+str);
    }

    @After("method()")
    public void bye(){
        System.out.println("bye");
    }

    @Around("method()")
    public Object compute(ProceedingJoinPoint pjp) throws Throwable {
        long start=System.currentTimeMillis();
        Object reVal=pjp.proceed();
        long end =System.currentTimeMillis();
        System.out.println("方法执行时间为:"+(end-start));
        return reVal;
    }
}
