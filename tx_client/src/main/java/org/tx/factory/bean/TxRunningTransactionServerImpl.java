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


        try {
            proceed = point.proceed();

        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            TxGroup group = addTransaction(groupId, kid, info.getInvocation().getMethodStr());


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
