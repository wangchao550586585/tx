package org.tx.service.impl;

import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tx.anno.TxTransaction;
import org.tx.feign.TTest2FeignClient;
import org.tx.feign.TTestFeignClient;
import org.tx.mapper.TTestMapper;
import org.tx.po.TTest;
import org.tx.service.TTestService;

/**
 * @author wangchao
 * @date 2020-11-27 13:07:21
 */
@Service
public class TTestServiceImpl extends ServiceImpl<TTestMapper, TTest> implements TTestService {
    @Autowired
    TTestFeignClient feignClient;
    @Autowired
    TTest2FeignClient feignClient2;

    @Override
    @TxTransaction(isStart = true)
    @Transactional
    public boolean saveTTest() {
        TTest tTest = new TTest();
        tTest.setName("WC");
        boolean save = this.save(tTest);
        R tx = feignClient.tx();
        R tx1 = feignClient2.tx();
        return save;
    }
}
