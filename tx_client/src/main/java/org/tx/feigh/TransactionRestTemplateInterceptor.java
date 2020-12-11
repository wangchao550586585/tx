package org.tx.feigh;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;
import org.tx.aop.entity.TxTransactionMode;
import org.tx.aop.interceptor.TxTransactionLocal;

import java.util.Objects;

/**
 * @author WangChao
 * @create 2020/12/12 6:16
 */
@Component
public class TransactionRestTemplateInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        TxTransactionLocal txTransactionLocal = TxTransactionLocal.current();
        String groupId = Objects.isNull(txTransactionLocal) ? null : txTransactionLocal.getGroupId();
        TxTransactionMode txTransactionMode = Objects.isNull(txTransactionLocal) ? null : txTransactionLocal.getMode();
        requestTemplate.header("tx-group", groupId);
        requestTemplate.header("tx-mode", txTransactionMode.name());
    }
}
