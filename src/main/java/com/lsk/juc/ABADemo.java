package com.lsk.juc;

import java.util.concurrent.atomic.AtomicReference;

public class ABADemo {
    private static AtomicReference<Integer> atomicReference = new AtomicReference<>(100);

    public static void main(String[] args) {
        //当有一个值从 A 改为 B 又改为 A，这就是 ABA 问题。
        new Thread(() -> {
            atomicReference.compareAndSet(100, 101);
            atomicReference.compareAndSet(101, 100);
        },"t1").start();

        new Thread(() -> {
            try {
                Thread.sleep(1000); // 保证上面线程先执行
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            atomicReference.compareAndSet(100, 2019);
            System.out.println(atomicReference.get()); // 2019
        },"t2").start();
    }
}
