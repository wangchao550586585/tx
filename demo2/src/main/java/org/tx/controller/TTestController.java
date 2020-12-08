package org.tx.controller;

import com.baomidou.mybatisplus.extension.api.R;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tx.service.TTestService;

/**
 * @author wangchao
 * @date 2020-11-27 13:07:21
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/ttest")
public class TTestController {

    private final TTestService tTestService;

    @GetMapping("/tx")
    public R tx() {
        tTestService.tx();
        return R.ok(null);
    }

}
