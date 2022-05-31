package com.lsk.juc;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 1验证volatile的可见性
 * 1.1 如果int num = 0，number变量没有添加volatile关键字修饰
 * 1.2 添加了volatile，可以解决可见性
 * <p>
 * 2.验证volatile不保证原子性
 * 2.1 原子性指的是什么
 * 不可分割、完整性，即某个线程正在做某个具体业务时，中间不可以被加塞或者被分割，需要整体完整，要么同时成功，要么同时失败
 */
public class VolatileDemo1 {

    public static void main(String[] args) {
//        visibilityByVolatile();//验证volatile的可见性
        atomicByVolatile();//验证volatile不保证原子性
    }

    /**
     * volatile可以保证可见性，及时通知其他线程，主物理内存的值已经被修改
     */
    //public static void visibilityByVolatile(){}

    /**
     * volatile不保证原子性
     * 以及使用Atomic保证原子性
     */
    public static void atomicByVolatile() {
        MyData1 myData = new MyData1();
        for (int i = 1; i <= 20; i++) {
            new Thread(() -> {
                for (int j = 1; j <= 1000; j++) {
                    myData.addSelf();
                    myData.atomicAddSelf();
                }
            }, "Thread " + i).start();
        }
        //等待上面的线程都计算完成后，再用main线程取得最终结果值
        try {
            TimeUnit.SECONDS.sleep(4);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while (Thread.activeCount() > 2) {
            Thread.yield();
        }
        System.out.println(Thread.currentThread().getName() + "\t finally num value is " + myData.num);
        System.out.println(Thread.currentThread().getName() + "\t finally atomicnum value is " + myData.atomicInteger);
    }
}

class MyData1 {
    //    int num = 0;
    volatile int num = 0;

    public void addToSixty() {
        this.num = 60;
    }

    public void addSelf() {
        num++;
    }

    AtomicInteger atomicInteger = new AtomicInteger();

    public void atomicAddSelf() {
        atomicInteger.getAndIncrement();
    }
}

/**
 执行三次结果为：
 //1.
 main	 finally num value is 19580
 main	 finally atomicnum value is 20000
 //2.
 main	 finally num value is 19999
 main	 finally atomicnum value is 20000
 //3.
 main	 finally num value is 18375
 main	 finally atomicnum value is 20000
 //使用volatile修饰的num并没有达到20000

 */