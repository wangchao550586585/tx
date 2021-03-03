package org.tx.aop.datasource;

import org.tx.aop.interceptor.TxTransactionLocal;
import org.tx.task.Task;
import org.tx.task.TaskGroup;
import org.tx.task.TaskGroupManager;

import java.sql.*;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

/**
 * @author WangChao
 * @create 2021/2/28 11:50
 */
public class LCNStartConnection extends DefaultConnection {
    String groupId;
    private Task waitTask;
    private volatile boolean isClose = false;

    public LCNStartConnection(Connection connection) {
        super(connection);
        this.groupId = TxTransactionLocal.current().getGroupId();
        TaskGroup taskGroup = TaskGroupManager.getInstance().createTaskGroup(groupId);
        waitTask = taskGroup.getCurrent();
    }

    @Override
    public void commit() throws SQLException {
        new Thread(() -> {
            // TODO: 2021/3/1  这里为啥null不知道
            TxTransactionLocal.setCurrent(null);
            try {
                waitTask.await();
                int rs = waitTask.getState();
                if (rs == 1) {
                    connection.commit();
                } else {
                    rollback();
                }
                waitTask.remove();
            } catch (SQLException e) {
                try {
                    rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            } finally {
                try {
                    connection.close();
                    isClose = true;
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void rollback() throws SQLException {
        connection.rollback();
        connection.close();
        isClose = true;
    }

    @Override
    public void close() throws SQLException {
        if (!isClose) {
            return;
        }
        //connection.close();
    }

}
