package org.tx.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
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

    @TxTransaction()
    @Override
    public void tx() {
        System.out.println("测试注解");
    }
}
