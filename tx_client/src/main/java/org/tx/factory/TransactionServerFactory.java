package org.tx.factory;

import org.springframework.beans.BeansException;
import org.springframework.stereotype.Component;
import org.tx.aop.entity.TxTransactionInfo;
import org.tx.factory.bean.TransactionServer;
import org.tx.netty.SocketManager;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wangchao
 * @description: TODO
 * @date 2020/12/712:54
 */
@Component
public class TransactionServerFactory
//        implements FactoryBean
{
    private static Map<TransactionServerType, TransactionServer> transactionServerMap = new ConcurrentHashMap<>();

    public TransactionServer getTransactionServer(TxTransactionInfo info) {
        if (!SocketManager.instance().isNetState()) {
            return transactionServerMap.get(TransactionServerType.DefaultTransactionServer);
        }
        return null;
    }

    public static void register(TransactionServerType transactionServerType, TransactionServer transactionServer) {
        transactionServerMap.put(transactionServerType, transactionServer);
    }
    /**
     * 获取事务处理类
     *
     * @return
     * @throws BeansException
     */
//    @Override
//    public TransactionServer getObject() throws BeansException {
//        if (!SocketManager.instance().isNetState()) {
//            return new DefaultTransactionServer();
//        }
//        return null;
//    }
//
//    @Override
//    public Class<?> getObjectType() {
//        return null;
//    }
//
//    @Override
//    public boolean isSingleton() {
//        return true;
//    }
}
