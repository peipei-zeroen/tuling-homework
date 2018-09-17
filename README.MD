### 欢迎来到这里

> 此项目为学习图灵学院的作业

1. 包 `org.zeroen.tuling.homework.lock` 是`JDK1.8` `StampedLock`的一个实现Demo
   - 模拟了一个类似秒杀的场景，90%读，10%买。
   - test负责定时产生这些请求。
   - Mointer负责监视所有商品是否库存为0，若是结束程序。

2. 包 `org.zeroen.tuling.homework.semaphore` 是以AOP+Semaphore实现的一个对Controller层的限流
   - RequestLimit注解定义信号量大小，若为负数或者未定义就视为不限流
   - SemaphoreAspect是限流逻辑的核心，主要采用Around的方式。

3. 包 `org.zeroen.tuling.homework.longadder`是`JDK1.8` `LongAdder`的一个实现Demo
   - 模拟了耗时的任务
   - 将模拟耗时的任务只保留了随机方法，10w次任务对比了`LongAdder`和`AtomicLong`执行情况
     总合计大致`LongAdder`比`AtomicLong`竞争少0.667ms
   