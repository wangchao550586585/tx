package org.tx;

/**
 * @author wangchao
 * @description: TODO
 * @date 2020/12/516:27
 */
public enum  TXType {

    //------------------------TXM
    //接收
    /**  sponsor  发起方
     *   partake  参与方
     * 发起方注册事务组
     * 发起方通知事务组
     * 发起方响应结果
     */
    REGISTER_FROM_SPONSOR(1),
    NOTIFY_FROM_SPONSOR(2),
    RESPONSE_FROM_SPONSOR(3),

    /**
     * 参与方加入事务组
     * 参与方响应结果
     */
    //发送
    ADD_TX_GROUP_FROM_PARTAKE(4),
    RESPONSE_FROM_PARTAKE(5),

    /**
     * 通知参与方提交回滚
     * 通知发起方响应
     */
    NOTIFY_EXEC_TO_PARTAKE(6),
    NOTIFY_TO_SPONSOR(7),

    //------------------------PARTAKE 参与方
/*    接收
            TXM通知提交回滚
            */

/*
发送
            加入TXM事务组
            响应TXM提交回滚结果*/
    REGISTER("register"),
    REGISTER("register"),
    REGISTER("register"),
    REGISTER("register"),
    REGISTER("register"),
    REGISTER("register"),

    //------------------------SPONSOR  发起方
    /**
     * 接收
     * TXM通知
     * */

    /**  发送
     * 注册TXM事务组
     * 通知TXM事务组
     * 响应TXM事务组
     */
    ;
    private final int type;

    TXType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
