package org.tx.aop.datasource;

import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.tx.aop.interceptor.TxTransactionLocal;
import org.tx.task.Task;
import org.tx.task.TaskGroup;
import org.tx.task.TaskGroupManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;

/**
 * @author WangChao
 * @create 2021/2/24 0:35
 */
@Component
public class LCNConnection implements ILCNConnection {

    @Override
    public Connection getConnection(ProceedingJoinPoint pj) throws Throwable {
        // TODO: 2021/2/24  这里连接复用,这样当同一个事务执行多个sql的时,保证是同一个连接.这里还需要具体看看spring-jdbc是否复用了连接
        return Optional.ofNullable(wrapperLcnConnection((Connection) pj.proceed()))
                .orElseThrow(() -> new SQLException("connection was overload"));
    }

    private Connection wrapperLcnConnection(Connection connection) {
        TxTransactionLocal txTransactionLocal = TxTransactionLocal.current();
        if (!Objects.isNull(txTransactionLocal) && StringUtils.hasLength(txTransactionLocal.getGroupId())) {
            return createConnection(txTransactionLocal, connection);
        }
        return connection;
    }

    private Connection createConnection(TxTransactionLocal txTransactionLocal, Connection connection) {
        //是否是发起方
        if (txTransactionLocal.isStart()) {
            LCNStartConnection lcnStartConnection = new LCNStartConnection(connection);
            // TODO: 2021/2/28 连接池复用
            return lcnStartConnection;
        }
        return connection;
    }
}
