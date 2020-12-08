package org.tx.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.tx.po.TTest;

/**
 * 
 *
 * @author wangchao
 * @date 2020-11-27 13:07:21
 */
public interface TTestService extends IService<TTest> {


    boolean saveTTest();
}
