package org.tx.aop.interceptor;

import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author WangChao
 * @create 2020/11/29 15:01
 */
@Component
public class TxManagerInterceptor {
    @Autowired
    private AspectBeforeService aspectBeforeService;


    public Object around(ProceedingJoinPoint pj) {
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = requestAttributes == null ? null : ((ServletRequestAttributes) requestAttributes).getRequest();
        String groupId = request == null ? null : request.getHeader("tx-group");
        String mode = request == null ? null : request.getHeader("tx-mode");
        return aspectBeforeService.around(groupId, mode, pj);
    }
}
