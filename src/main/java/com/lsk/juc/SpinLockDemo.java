package com.lsk.juc;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class SpinLockDemo {
    // 原子引用线程
    AtomicReference<Thread> atomicReference = new AtomicReference<>();

    public void mylock() {
        Thread thread = Thread.currentThread();
        System.out.println(Thread.currentThread().getName() + "\t come in");
        //获取锁的时候，如果原子引用为空就获取锁，不为空表示有人获取了锁，就循环等待。
        while (!atomicReference.compareAndSet(null, thread)) {

        }
    }

    public void myUnlock() {
        Thread thread = Thread.currentThread();
        atomicReference.compareAndSet(thread, null);
        System.out.println(Thread.currentThread().getName() + "\t myunlock()");
    }

    public static void main(String[] args) {
        /*
         *通过CAS操作完成自旋锁，线程1先进来调用mylock方法自己持有锁3秒钟，线程2随后进来发现当前有线程
         * *持有锁，不是null，所以只能通过自旋等待，直到A释放锁后B随后抢到
         */
        SpinLockDemo spinLockDemo = new SpinLockDemo();

        new Thread(() -> {
            spinLockDemo.mylock();
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (Exception e) {
                e.printStackTrace();
            }
            spinLockDemo.myUnlock();
        }, "Thread 1").start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            spinLockDemo.mylock();
            spinLockDemo.myUnlock();
        }, "Thread 2").start();
    }
}

/**
 Thread 1	 come in
 Thread 2	 come in //循环等待	,直到线程1释放锁
 Thread 1	 myunlock()
 Thread 2	 myunlock()
 */