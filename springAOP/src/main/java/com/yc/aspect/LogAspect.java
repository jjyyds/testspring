package com.yc.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

//切面类：要增强的功能
@Aspect
@Component
@Order(1)
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
    public void bye(JoinPoint jp){
        System.out.println("bye========================");
        Object target=jp.getTarget();
        System.out.println("目标类是"+target);
        System.out.println("方法"+jp.getSignature());
        Object []objs=jp.getArgs();
        if(objs!=null){
            for(Object o:objs){
                System.out.println("参数"+o);
            }
        }
        System.out.println("bye========================");
    }

    @Around("execution(* com.yc.biz.StudentBizImpl.update*(..))")
    public Object compute(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("compute进入");
        long start=System.currentTimeMillis();
        Object reVal=pjp.proceed();
        long end =System.currentTimeMillis();
        System.out.println("方法执行时间为:"+(end-start));
        return reVal;
    }
}
