package org.tx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @author WangChao
 * @create 2020/12/7 8:26
 */
@Component
public class ServerListener implements ApplicationListener<WebServerInitializedEvent> {
    @Autowired
    NettyService nettyService;

    @Override
    public void onApplicationEvent(WebServerInitializedEvent webServerInitializedEvent) {
        Thread thread = new Thread(() -> {
            nettyService.start();
        });
        thread.setName("Tx-init");
        thread.start();
    }
}
