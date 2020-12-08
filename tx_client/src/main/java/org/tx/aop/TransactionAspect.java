package org.tx.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.tx.aop.interceptor.TxManagerInterceptor;

/**
 * @author WangChao
 * @create 2020/11/27 8:14
 */
@Aspect
@Component
public class TransactionAspect implements Ordered {
    @Autowired
    private TxManagerInterceptor txManagerInterceptor;

    @Around("@annotation(org.tx.anno.TxTransaction)")
    public Object around(ProceedingJoinPoint pj) throws Throwable {
        System.out.println("环绕前");
//        Object around = pj.proceed();// 执行目标方法
        Object around = txManagerInterceptor.around(pj);
//        System.out.println("环绕后");
        return around;
    }

    @Override
    public int getOrder() {
        return 1000;
    }
}
