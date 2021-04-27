package com.yc.starter;

import com.yc.IDBHelper;
import com.yc.services.IHelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;

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

    @Autowired
    private IDBHelper idbHelper;

    @GetMapping("/mysql")
    public String mysql(){
        Connection con=idbHelper.getConnection();
        String constr=con.toString();
        return constr;
    }

}
