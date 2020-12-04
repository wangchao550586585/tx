package org.tx;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;


@SpringBootApplication
@Configuration
public class TXManagerApp {

    public static void main(String[] args) {
        SpringApplication.run(TXManagerApp.class, args);
    }


}


