package org.zeroen.tuling.homework.semaphore.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.zeroen.tuling.homework.semaphore.anno.RequestLimit;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @Author
 * @Description
 * @Date Created in 18:52 2018/9/16
 * @Modified By：
 */
@Component
@Aspect
public class SemaphoreAspect {

    private Map<Method, Semaphore> cache = new HashMap<>();

    @Pointcut("execution(public * org.zeroen.tuling.homework.semaphore.controller..*(..))")
    public void semaphoreAopPointcut() {
    }

    @Around("semaphoreAopPointcut()")
    public Object semaphoreAopAround(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method target = signature.getMethod();

        Semaphore semaphore = null;
        if (cache.containsKey(target)) {
            semaphore = cache.get(target);
        } else {
            RequestLimit limit = null;
            if (target.isAnnotationPresent(RequestLimit.class)) {
                limit = target.getAnnotation(RequestLimit.class);
            } else if (target.getDeclaringClass().isAnnotationPresent(RequestLimit.class)) {
                limit = target.getDeclaringClass().getAnnotation(RequestLimit.class);
            }

            if (limit != null && limit.value() > 0) {
                semaphore = new Semaphore(limit.value());
            }
            cache.put(target, semaphore);
        }

        if (semaphore != null) {
            try {
                boolean success = semaphore.tryAcquire(1, TimeUnit.SECONDS);
                if (!success)
                    throw new RuntimeException("time out for waiting 1 seconds");
            } catch (InterruptedException e) {
                if (Thread.currentThread().isInterrupted()) {
                    throw new RuntimeException("程序被中断了");
                }
            }
        }

        try {
            return joinPoint.proceed() + "-" + Thread.currentThread().getName();
        } catch (Throwable e) {
            e.printStackTrace();
            throw new RuntimeException("程序有异常", e);
        } finally {
            if (semaphore != null) {
                semaphore.release();
            }
        }
    }


}
