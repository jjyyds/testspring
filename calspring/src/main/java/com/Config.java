package com;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import java.util.Random;

@Configuration
@ComponentScan("com")
public class Config {

    @Bean
    public Random r(){
        return new Random();
    }
}
