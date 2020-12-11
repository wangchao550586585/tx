package org.tx;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.tx.redis.RedisService;
import org.tx.redis.RedisServiceImpl;

/**
 * Unit test for simple App.
 */
@SpringBootTest
public class AppTest {
    @Autowired
    RedisService redisService;

    @Test
    public void textTx() {
        redisService.saveTransaction(RedisServiceImpl.key_prefix, "xxxx");
    }


}
