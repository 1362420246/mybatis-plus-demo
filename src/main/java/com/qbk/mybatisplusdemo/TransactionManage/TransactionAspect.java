package com.qbk.mybatisplusdemo.TransactionManage;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.lang.reflect.Method;
import java.util.Stack;

/**
 * 多数据源事务切面
 */
@Slf4j
//@Aspect
//@Component
public class TransactionAspect {

    @Autowired
    private ApplicationContext context;

//    @Around(value = "@within(org.springframework.stereotype.Service)")
//    public Object serviceTransaction(ProceedingJoinPoint point) {
//        log.info("@Service注解的AOP事务支持. . . ");
//        return this.handleTransaction(point);
//    }

    @Around(value = "@annotation(com.qbk.mybatisplusdemo.TransactionManage.TransactionSupport)")
    public Object transactionSupport(ProceedingJoinPoint point) {
        log.info("自定义@TransactionSupport注解的AOP事务支持. . . ");
        return this.handleTransaction(point);
    }

    private Object handleTransaction(ProceedingJoinPoint point) {
        Object result = null;
        Stack<DataSourceTransactionManager> dataSourceTransactionManagerStack = new Stack<>();
        Stack<TransactionStatus> transactionStatuStack = new Stack<>();
        boolean tx = false;
        try {
            TransactionSupport classTx = point.getTarget().getClass().getAnnotation(TransactionSupport.class);

            MethodSignature signature = (MethodSignature) point.getSignature();
            Method method = signature.getMethod();
            TransactionSupport methodTx = method.getAnnotation(TransactionSupport.class);

            TransactionSupport[] mtxes = {classTx, methodTx};
            tx = openTransaction(dataSourceTransactionManagerStack, transactionStatuStack, mtxes);
            result = point.proceed();
            if (tx) {
                commit(dataSourceTransactionManagerStack, transactionStatuStack);
            }
        } catch (Throwable e) {
            if (tx) {
                rollback(dataSourceTransactionManagerStack, transactionStatuStack);
            }
            e.printStackTrace();
        } finally {
        }
        return result;
    }

    /**
     * 开启事务处理方法
     */
    private boolean openTransaction(Stack<DataSourceTransactionManager> dataSourceTransactionManagerStack,
                                    Stack<TransactionStatus> transactionStatusStack, TransactionSupport[] mtxes) {
        boolean[] re = {false};
        if (mtxes != null) {
            for (TransactionSupport mtx : mtxes) {
                if (mtx != null) {
                    String[] transactionMangerNames = mtx.value();
                    if (transactionMangerNames != null) {
                        for (String tname : transactionMangerNames) {
                            re[0] = true;
                            DataSourceTransactionManager dataSourceTransactionManager = (DataSourceTransactionManager) context.getBean(tname);
                            TransactionStatus transactionStatus = dataSourceTransactionManager
                                    .getTransaction(new DefaultTransactionDefinition());
                            transactionStatusStack.push(transactionStatus);
                            dataSourceTransactionManagerStack.push(dataSourceTransactionManager);
                        }
                    }
                }
            }
        }
        return re[0];
    }

    /**
     * 提交处理
     */
    private void commit(Stack<DataSourceTransactionManager> dataSourceTransactionManagerStack,
                        Stack<TransactionStatus> transactionStatuStack) {
        while (!dataSourceTransactionManagerStack.isEmpty()) {
            dataSourceTransactionManagerStack.pop().commit(transactionStatuStack.pop());
        }
    }

    /**
     * 回滚处理
     */
    private void rollback(Stack<DataSourceTransactionManager> dataSourceTransactionManagerStack,
                          Stack<TransactionStatus> transactionStatuStack) {
        while (!dataSourceTransactionManagerStack.isEmpty()) {
            dataSourceTransactionManagerStack.pop().rollback(transactionStatuStack.pop());
        }
    }
}
