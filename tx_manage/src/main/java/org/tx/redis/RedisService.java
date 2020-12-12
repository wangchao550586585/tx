package org.tx.redis;

import org.tx.netty.TxGroup;

/**
 * @author wangchao
 * @description: TODO
 * @date 2020/12/11 8:55
 */
public interface RedisService {
    void saveTransaction(String key, String value);

    TxGroup getTransaction(String key);

    void del(String key);
}
