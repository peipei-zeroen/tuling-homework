### 欢迎来到这里

> 此项目为学习图灵学院的作业

1. 包 `org.zeroen.tuling.homework.lock` 是`JDK1.8` `StampedLock`的一个实现Demo
   - 模拟了一个类似秒杀的场景，90%读，10%买。
   - test负责定时产生这些请求。
   - Mointer负责监视所有商品是否库存为0，若是结束程序。
