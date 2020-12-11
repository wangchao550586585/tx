package org.tx.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

/**
 * @author wangchao
 * @description: TODO
 * @date 2020/12/11 8:57
 */
@Component
public class RedisServiceImpl implements RedisService {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public static final String key_prefix = "tx:manager:default";

    @Override
    public void saveTransaction(String key, String value) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key, value);
    }

    @Override
    public void getTransaction(String key) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.get(key);

    }

    @Override
    public void del(String key) {

    }
}
