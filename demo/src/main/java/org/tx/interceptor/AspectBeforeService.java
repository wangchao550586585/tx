package org.tx.interceptor;

import org.tx.anno.TxTransaction;
import lombok.SneakyThrows;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @author WangChao
 * @create 2020/11/29 15:03
 */
@Component
public class AspectBeforeService {
    @SneakyThrows
    public Object around(String groupId, String mode, ProceedingJoinPoint pj) {
        MethodSignature signature = (MethodSignature) pj.getSignature();
        //这里仅仅获取接口执行方法
        Method method = signature.getMethod();

        Class<?> clazz = pj.getTarget().getClass();
        Object[] args = pj.getArgs();
        //获取具体执行的子类方法
        Method execMethod = clazz.getMethod(method.getName(), method.getParameterTypes());

        //获取分布式事务注解
        TxTransaction txTransaction = execMethod.getAnnotation(TxTransaction.class);

        //封装事务方法
        TransactionInvocation invocation = new TransactionInvocation(clazz, execMethod.getName(), method.getParameterTypes(), args, execMethod.toString());
        TxTransactionLocal txTransactionLocal = TxTransactionLocal.current();

        //封装事务信息
        TxTransactionInfo info = new TxTransactionInfo(txTransaction, invocation, groupId, txTransactionLocal);
        //设置事务类型
        mode = Objects.isNull(mode) ? "TX_MODE_LCN" : mode;
        info.setMode(TxTransactionMode.valueOf(mode));

        //创建事务服务类


        return null;
    }
}
