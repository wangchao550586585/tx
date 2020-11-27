package com.tx.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

/**
 * @author WangChao
 * @create 2020/11/27 8:14
 */
@Aspect
@Component
public class TransactionAspect implements Ordered {

    @Around("@annotation(com.tx.anno.TxTransaction)")
    public void around(ProceedingJoinPoint pj) throws Throwable {
        System.out.println("环绕前");
        pj.proceed();// 执行目标方法
        System.out.println("环绕后");
    }

    @Override
    public int getOrder() {
        return 1000;
    }
}
