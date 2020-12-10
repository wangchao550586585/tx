package org.tx.aop.interceptor;

import lombok.Data;
import org.tx.anno.TxTransaction;
import org.tx.aop.entity.TxTransactionMode;

/**
 * @author WangChao
 * @create 2020/11/29 17:22
 */
@Data
public class TxTransactionLocal {
    private String groupId;
    private boolean start;
    private TxTransactionMode mode;
    private static final ThreadLocal<TxTransactionLocal> currentLocal = new InheritableThreadLocal();

    public static TxTransactionLocal current() {
        return currentLocal.get();
    }

    public static void setCurrent(TxTransactionLocal txTransactionLocal) {
        currentLocal.set(txTransactionLocal);
    }
}
