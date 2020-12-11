package org.tx.aop.entity;

import lombok.Data;
import org.springframework.util.ObjectUtils;
import org.tx.anno.TxTransaction;
import org.tx.aop.interceptor.TxTransactionLocal;

import java.util.Objects;

/**
 * @author WangChao
 * @create 2020/11/29 17:24
 */
@Data
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

    /**
     * 是否是事务发起方
     * @return
     */
    public boolean isTXStart() {
        return !Objects.isNull(this.getTxTransaction()) && this.getTxTransaction().isStart()
                && null == this.getTxTransactionLocal()
                && ObjectUtils.isEmpty(this.getGroupId());
    }
    /**
     * 是否是事务参与方
     * @return
     */
    public boolean isTXRunning() {
        return null != this.getTxTransactionLocal()
               || !ObjectUtils.isEmpty(this.getGroupId());
    }
}
