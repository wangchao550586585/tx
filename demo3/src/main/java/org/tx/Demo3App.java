package org.tx;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;


@SpringBootApplication
@Configuration
@EnableFeignClients
public class Demo3App {

    public static void main(String[] args) {
        SpringApplication.run(Demo3App.class, args);
    }


}


