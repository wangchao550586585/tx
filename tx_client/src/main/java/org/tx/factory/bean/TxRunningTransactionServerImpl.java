package org.tx.factory.bean;

import com.alibaba.fastjson.JSONObject;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.tx.aop.entity.TxTransactionInfo;
import org.tx.aop.interceptor.TxTransactionLocal;
import org.tx.factory.TransactionServerFactory;
import org.tx.factory.TransactionServerType;
import org.tx.netty.SocketManager;
import org.tx.task.Task;
import org.tx.task.TaskGroupManager;

/**
 * @author wangchao
 * @description: TODO
 * @date 2020/12/7 13:31
 */
@Component
public class TxRunningTransactionServerImpl implements TransactionServer, InitializingBean {
    @Override
    public Object execute(ProceedingJoinPoint point, TxTransactionInfo info) throws Throwable {
        Object proceed = null;
        String kid = Utils.buildUUID();
        String groupId = info.getGroupId();

        TxTransactionLocal txTransactionLocal = new TxTransactionLocal();
        txTransactionLocal.setGroupId(groupId);
        txTransactionLocal.setMode(info.getMode());
        txTransactionLocal.setKid(kid);
        txTransactionLocal.setStart(false);
        TxTransactionLocal.setCurrent(txTransactionLocal);

/**
 * 执行成功
 *     尚未连上txm,
 *          通知事务组其他回滚，抛出异常
 *     连上txm
 *          等待txm通知是否回滚,若是则抛出异常
 *
 *
 * 执行失败(这里尚未加入事务组)
 *     通知事务组其他回滚，抛出异常
 */
        try {
            proceed = point.proceed();
            //加入事务组
            TxGroup group = addTransaction(groupId, kid, info.getInvocation().getMethodStr());
            Task task = TaskGroupManager.getInstance().getTask(kid);



        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            TxTransactionLocal.setCurrent(null);
        }

        return proceed;
    }

    private TxGroup addTransaction(String groupId, String taskId, String methodStr) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("g",groupId);
        jsonObject.put("t",taskId);
        jsonObject.put("ms",methodStr);

        Request request = new Request("atg", jsonObject.toString());
        String json = SocketManager.instance().sendMsg(request);
        return TxGroup.parse(json);
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        TransactionServerFactory.register(TransactionServerType.txRunning, this);
    }
}
