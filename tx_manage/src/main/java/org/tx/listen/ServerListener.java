package org.tx.listen;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.tx.netty.TxmNettyService;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author wangchao
 * @description: TODO
 * @date 2020/12/11 8:47
 */
@Component
public class ServerListener implements ServletContextListener {
    @Autowired
    TxmNettyService txmNettyService;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        txmNettyService.start();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        txmNettyService.close();
    }
}
