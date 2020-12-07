package org.tx.aop.entity;

/**
 * @author WangChao
 * @create 2020/11/29 17:27
 */
public enum TxTransactionMode {
    /**
     * LCN 模式
     */
    TX_MODE_LCN("LCN 模式,2阶段提交 读提交"),

    /**
     * TXC 模式
     */
    TX_MODE_TXC("TXC 模式,未提交读(READ UNCOMMITTED)");
    private String description;

    TxTransactionMode(String description) {
        this.description = description;
    }
}
