package org.tx.factory;

import org.springframework.beans.BeansException;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.tx.aop.entity.TxTransactionInfo;
import org.tx.factory.bean.TransactionServer;
import org.tx.netty.SocketManager;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wangchao
 * @description: TODO
 * @date 2020/12/7 12:54
 */
@Component
public class TransactionServerFactory
//        implements FactoryBean
{
    private static Map<TransactionServerType, TransactionServer> transactionServerMap = new ConcurrentHashMap<>();

    public TransactionServer getTransactionServer(TxTransactionInfo info) {
        //通讯不正常,直接返回
        if (!SocketManager.instance().isNetState()) {
            return transactionServerMap.get(TransactionServerType.txDefault);
        }
        //说明是事务发起方
        if (info.isTXStart()) {
            if (SocketManager.instance().isNetState()) {
                return transactionServerMap.get(TransactionServerType.txStart);
            } else {
                return transactionServerMap.get(TransactionServerType.txDefault);
            }
        }

        //事务参与方
        if (info.isTXRunning()){
            if (SocketManager.instance().isNetState()) {
                return transactionServerMap.get(TransactionServerType.txRunning);
            } else {
                return transactionServerMap.get(TransactionServerType.txDefault);
            }
        }

        return transactionServerMap.get(TransactionServerType.txDefault);
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
