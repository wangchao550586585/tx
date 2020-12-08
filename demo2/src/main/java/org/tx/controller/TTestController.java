package org.tx.controller;

import com.baomidou.mybatisplus.extension.api.R;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
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


    @PostMapping("/tx")
    public R tx() {
        return R.ok(tTestService.saveTTest());
    }
}
