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
        TransactionServerType txType = TransactionServerType.txDefault;
        //通讯不正常,直接返回
        if (!SocketManager.instance().isNetState()) {
        }
        //说明是事务发起方
        else if (info.isTXStart()) {
            txType = TransactionServerType.txStart;
        }
        //事务参与方
        else if (info.isTXRunning()) {
            txType = TransactionServerType.txRunning;
        }
        return getTransactionServer(txType);
    }

    private TransactionServer getTransactionServer(TransactionServerType txType) {
        return transactionServerMap.get(txType);
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
