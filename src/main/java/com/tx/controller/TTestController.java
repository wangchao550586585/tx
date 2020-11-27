package com.tx.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tx.model.po.TTest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import com.tx.service.TTestService;

/**
 * 
 *
 * @author wangchao
 * @date 2020-11-27 13:07:21
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/ttest" )
public class TTestController {

    private final TTestService tTestService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param tTest 
     * @return R
     */
    @GetMapping("/page" )
    public R page(Page page, TTest tTest) {
        return R.ok(tTestService.page(page, Wrappers.query(tTest)));
    }


    /**
     * 通过id查询
     * @param id id
     * @return R
     */
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Long id) {
        return R.ok(tTestService.getById(id));
    }

    /**
     * 新增
     * @param tTest 
     * @return R
     */
    @PostMapping
    public R save(@RequestBody TTest tTest) {
        return R.ok(tTestService.save(tTest));
    }

    /**
     * 修改
     * @param tTest 
     * @return R
     */
    @PutMapping
    public R updateById(@RequestBody TTest tTest) {
        return R.ok(tTestService.updateById(tTest));
    }

    /**
     * 通过id删除
     * @param id id
     * @return R
     */
    @DeleteMapping("/{id}" )
    public R removeById(@PathVariable Long id) {
        return R.ok(tTestService.removeById(id));
    }

}
