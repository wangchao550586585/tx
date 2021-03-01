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
     //事务组id
    private String groupId;
    //是否是事务开启的一方
    private boolean start=false;
    //事务模式
    private TxTransactionMode mode;
    //
    private String kid;

    private static final ThreadLocal<TxTransactionLocal> currentLocal = new InheritableThreadLocal();

    public static TxTransactionLocal current() {
        return currentLocal.get();
    }

    public static void setCurrent(TxTransactionLocal txTransactionLocal) {
        currentLocal.set(txTransactionLocal);
    }
}
