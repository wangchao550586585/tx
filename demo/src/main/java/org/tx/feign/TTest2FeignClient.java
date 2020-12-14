package org.tx.feign;

import com.baomidou.mybatisplus.extension.api.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author wangchao
 * @description: TODO
 * @date 2020/12/8 17:03
 */
@FeignClient(name = "tx-demo3")
public interface TTest2FeignClient {
    @PostMapping("/ttest/tx")
    public R tx();
}
