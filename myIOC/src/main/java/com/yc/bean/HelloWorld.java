package com.yc.bean;

import com.yc.springframework.stereotype.PostConstruct;
import com.yc.springframework.stereotype.PreDestroy;

//@Component
public class HelloWorld{

    @PostConstruct
    public void setup(){
        System.out.println("PostConstruct");
    }

    @PreDestroy
    public void destroy(){
        System.out.println("PreDestroy");
    }

    public HelloWorld(){
        System.out.println("构造");
    }

    public void show(){
        System.out.println("hello world");
    }
}
