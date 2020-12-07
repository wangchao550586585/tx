package org.tx.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author wangchao
 * @description: TODO
 * @date 2020/12/710:17
 */
@ConfigurationProperties(prefix = "tx")
@Data
@Component
public class TxConfig {
    private String host = "127.0.0.1";
    private int port = 8080;
}
