package org.tx.factory.bean;

import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.tx.aop.entity.TxTransactionInfo;
import org.tx.factory.TransactionServerFactory;
import org.tx.factory.TransactionServerType;

/**
 * @author wangchao
 * @description: TODO
 * @date 2020/12/7 13:16
 */
@Component
public class DefaultTransactionServerImpl implements  TransactionServer  , InitializingBean {
    @Override
    public Object execute(ProceedingJoinPoint point, TxTransactionInfo info) throws Throwable {
        return point.proceed();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        TransactionServerFactory.register(TransactionServerType.txDefault,this);
    }
}
