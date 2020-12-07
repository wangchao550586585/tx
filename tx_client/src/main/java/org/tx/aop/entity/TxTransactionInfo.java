package org.tx.aop.entity;

import org.tx.anno.TxTransaction;
import org.tx.aop.interceptor.TxTransactionLocal;

/**
 * @author WangChao
 * @create 2020/11/29 17:24
 */
public class TxTransactionInfo {
    private TxTransaction txTransaction;
    private TransactionInvocation invocation;
    private String groupId;
    private TxTransactionLocal txTransactionLocal;
    private TxTransactionMode mode;

    public TxTransactionInfo(TxTransaction txTransaction, TransactionInvocation invocation, String groupId, TxTransactionLocal txTransactionLocal) {
        this.txTransaction = txTransaction;
        this.invocation = invocation;
        this.groupId = groupId;
        this.txTransactionLocal = txTransactionLocal;
    }

    public void setMode(TxTransactionMode mode) {
        this.mode = mode;
    }
}
