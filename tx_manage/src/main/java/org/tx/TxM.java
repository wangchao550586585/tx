package org.tx;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

/**
 * @author wangchao
 * @description: TODO
 * @date 2020/12/11 8:20
 */

@SpringBootApplication
@Configuration
@EnableFeignClients
public class TxM {
    public static void main(String[] args) {
        SpringApplication.run(TxM.class, args);
    }
}
