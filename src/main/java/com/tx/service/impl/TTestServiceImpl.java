package com.tx.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tx.anno.TxTransaction;
import com.tx.mapper.TTestMapper;
import com.tx.model.po.TTest;
import com.tx.service.TTestService;
import org.springframework.stereotype.Service;

/**
 * @author wangchao
 * @date 2020-11-27 13:07:21
 */
@Service
public class TTestServiceImpl extends ServiceImpl<TTestMapper, TTest> implements TTestService {

    @TxTransaction
    @Override
    public void tx() {
        System.out.println("测试注解");
    }
}
