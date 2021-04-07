package com.yc.bean;

import com.yc.springframework.stereotype.Autowired;
import com.yc.springframework.stereotype.Component;

@Component
public class Person {
//    @Resource(name = "hw1")
    @Autowired
    private HelloWorld hw;

//    @Autowired
//    @Resource(name = "hw")
//    public void setHw(HelloWorld hw){
//        this.hw=hw;
//    }

    public HelloWorld getHw() {
        return hw;
    }
}
