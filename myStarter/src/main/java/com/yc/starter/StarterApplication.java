package com.yc.starter;

import com.yc.services.IHelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class StarterApplication {
    public static void main(String[] args) {
        SpringApplication.run(StarterApplication.class,args);
    }

    @Autowired
    private IHelloService helloService;

    @GetMapping("/hello")
    public String hello(){
        return helloService.say();
    }

}
