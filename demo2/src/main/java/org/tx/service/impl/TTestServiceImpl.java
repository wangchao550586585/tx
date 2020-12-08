package org.tx.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tx.anno.TxTransaction;
import org.tx.mapper.TTestMapper;
import org.tx.po.TTest;
import org.tx.service.TTestService;

/**
 * @author wangchao
 * @date 2020-11-27 13:07:21
 */
@Service
public class TTestServiceImpl extends ServiceImpl<TTestMapper, TTest> implements TTestService {

    @TxTransaction
    @Transactional
    @Override
    public boolean saveTTest() {
        TTest tTest = new TTest();
        tTest.setName("WC");
        return this.save(tTest);
    }
}
