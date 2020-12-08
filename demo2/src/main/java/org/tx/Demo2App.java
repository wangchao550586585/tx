package org.tx;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;


@SpringBootApplication
@Configuration
@EnableFeignClients
public class Demo2App {

    public static void main(String[] args) {
        SpringApplication.run(Demo2App.class, args);
    }


}


