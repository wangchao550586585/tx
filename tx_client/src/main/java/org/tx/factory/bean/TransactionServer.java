package org.tx.factory.bean;

import org.aspectj.lang.ProceedingJoinPoint;
import org.tx.aop.entity.TxTransactionInfo;

/**
 * @author wangchao
 * @description: TODO
 * @date 2020/12/7 13:17
 */
public interface TransactionServer {
    public Object execute(ProceedingJoinPoint point, TxTransactionInfo info) throws Throwable;
}
