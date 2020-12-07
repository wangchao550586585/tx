package org.tx.aop.entity;

import lombok.Data;

/**
 * 主要记录事务执行的方法信息
 * @author WangChao
 * @create 2020/11/29 15:22
 */
@Data
public class TransactionInvocation {
    private Class<?> targetClazz;
    private String method;
    private Class<?>[] parameterTypes;
    private Object[] argumentValues;
    private String methodStr;

    public TransactionInvocation(Class<?> targetClazz, String method, Class<?>[] parameterTypes, Object[] argumentValues, String methodStr) {
        this.targetClazz = targetClazz;
        this.method = method;
        this.parameterTypes = parameterTypes;
        this.argumentValues = argumentValues;
        this.methodStr = methodStr;
    }
}
