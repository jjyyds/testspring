package com.yc;

import com.yc.bean.HelloWorld;
import com.yc.springframework.stereotype.Bean;
import com.yc.springframework.stereotype.ComponentScan;
import com.yc.springframework.stereotype.Configuration;

@Configuration
@ComponentScan(classes = {"com.yc.bean"})
public class Config {

    @Bean
    public HelloWorld hw(){
        return new HelloWorld();
    }

    @Bean
    public HelloWorld hw1(){
        return new HelloWorld();
    }

}
