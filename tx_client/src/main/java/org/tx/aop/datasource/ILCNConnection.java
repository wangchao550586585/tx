package org.tx.aop.datasource;

import org.aspectj.lang.ProceedingJoinPoint;

import java.sql.Connection;

/**
 * @author WangChao
 * @create 2021/2/24 0:02
 */
public interface ILCNConnection {
    Connection getConnection(ProceedingJoinPoint pj)throws Throwable ;
}
