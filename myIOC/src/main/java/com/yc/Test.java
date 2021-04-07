package com.yc;

import com.yc.bean.Person;
import com.yc.springframework.context.AnnotationConfigApplicationContext;
import com.yc.springframework.context.ApplicationContext;

public class Test {
    public static void main(String[] args) {
        ApplicationContext ac=new AnnotationConfigApplicationContext(Config.class);
        //HelloWorld hw = (HelloWorld) ac.getBean("hw");
        //hw.show();
        Person p=(Person) ac.getBean("person");
        p.getHw().show();
    }
}
