package org.tx.aop.datasource;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;

/**
 * @author WangChao
 * @create 2021/2/23 23:55
 */
@Aspect
@Component
public class DataSourceAspect {
    @Autowired
    ILCNConnection lcnConnection;

    @Around("execution(* javax.sql.DataSource.getConnection(..))")
    public Connection around(ProceedingJoinPoint pj) throws Throwable {
        return lcnConnection.getConnection(pj);
    }
}
