package com.gjw.gjwblog.aop;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Scope;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 锁切面 springboot使用aop 加场景启动器就不用配置，直接进行使用
 *
 * @author gjw
 */
@Component
@Scope
@Aspect
@Order(1)
//order越小越是最先执行，但更重要的是最先执行的最后结束。order默认值是2147483647
public class LockAspect {

    private static Lock lock = new ReentrantLock();// 互斥锁 参数默认false，不公平锁

    /**
     * 锁的切入点 只要有@ThreadLock注解用过的方法就进行锁增强
     */
    @Pointcut("@annotation(com.gjw.gjwblog.annotation.ThreadLock)")
    public void lockAspect() {

    }

    /**
     * 实现环绕增强
     *
     * @param joinPoint
     * @return
     */
    @Around("lockAspect()")
    public Object around(ProceedingJoinPoint joinPoint) {

        Object obj = null;
        lock.lock();//上锁
        try {
            joinPoint.proceed();//执行环绕增强的方法
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return obj;
    }
}
