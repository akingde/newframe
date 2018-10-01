package com.newframe.interceptor;

import com.google.common.collect.Sets;
import com.newframe.services.account.AccountStatisService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.PropertyUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * 拦截器：订单
 */
@Slf4j
@Aspect
@Component
public class OrderInterceptor {
    @Autowired
    private AccountStatisService accountStatisService;

    /**
     * 保存或更新订单
     * 定义拦截规则
     */
    @Pointcut("execution(* com.newframe.services.account.AccountService.saveAccountRenterRepay(..))" +
            "||execution(* com.newframe.services.account.AccountService.updateAccountRenterRepay(..))")
    public void orderUpdatePointcut() {
    }

    /**
     * 供应商发货
     * 定义拦截规则
     */
    @Pointcut("execution(* com.newframe.services.order.OrderService.supplierDeliver(..))")
    public void supplierDeliverPointcut() {
    }


    /**
     * 拦截器具体实现
     */
    @AfterReturning("orderUpdatePointcut()")
    public Object orderUpdateInterceptor(JoinPoint point) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Objects.requireNonNull(null == point.getArgs()[0]);
        if (point.getArgs()[0] instanceof List) {
            List list = (List) point.getArgs()[0];
            Set<Long> uids = Sets.newHashSet();
            list.forEach(item -> {
                try {
                    uids.add((Long) PropertyUtils.getProperty(item, "uid"));
                } catch (Exception e) {
                    log.error(e.getMessage());
                }
            });
            uids.forEach(accountStatisService::statisAccountFunding);
            uids.forEach(accountStatisService::statisAccountLessor);
        } else {
            Long uid = (Long) PropertyUtils.getProperty(point.getArgs()[0], "uid");
            accountStatisService.statisAccountFunding(uid);
            accountStatisService.statisAccountLessor(uid);
        }
        return null;
    }

    /**
     * 发货时，更新统计值
     */
    @AfterReturning("supplierDeliverPointcut()")
    public Object supplierDeliverInterceptor(JoinPoint point) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Long uid = (Long) PropertyUtils.getProperty(point.getArgs()[0], "uid");
        accountStatisService.statisAccountSupplier(uid);
        return null;
    }

}
