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
 * @date 2020/12/8 8:31
 */
@Component
public class TxStartTransactionServerImpl implements TransactionServer, InitializingBean {
    @Override
    public Object execute(ProceedingJoinPoint point, TxTransactionInfo info) throws Throwable {
        Object proceed = null;
        //构建事务组
        String groupId = Utils.buildUUID();
        //注册事务
        registerTxGroup(groupId);

        TxTransactionLocal txTransactionLocal = new TxTransactionLocal();
        txTransactionLocal.setGroupId(groupId);
        txTransactionLocal.setMode(info.getMode());
        txTransactionLocal.setStart(true);
        TxTransactionLocal.setCurrent(txTransactionLocal);

        int state = 0;
        try {
            proceed = point.proceed();
            state = 1;
        } catch (Throwable throwable) {
            throw throwable;
        } finally {
            //判断补偿
            //通知事务,并上报当前端执行结果情况.
            int rs = closeTxGroup(groupId, state);
            //rs=0表示远端出问题,如果远端没问题,就查看是否当前端出问题
            int result = rs == 0 ? 0 : state;


        }
        return proceed;
    }

    private int closeTxGroup(String groupId, int state) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("g", groupId);
        jsonObject.put("s", state);
        Request request = new Request("ctg", jsonObject.toString());
        SocketManager.instance().sendMsg(request);

        return state;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        TransactionServerFactory.register(TransactionServerType.txStart, this);
    }

    private void registerTxGroup(String groupId) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("g", groupId);
        Request request = new Request("cg", jsonObject.toString());
        SocketManager.instance().sendMsg(request);
    }
}
