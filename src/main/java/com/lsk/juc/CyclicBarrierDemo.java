package com.lsk.juc;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierDemo {
    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(4, () -> {
            System.out.println("车满了，开始出发...");
        });
        for (int i = 0; i < 8; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + " 开始上车...");
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}

/**
 Thread-0 开始上车...
 Thread-1 开始上车...
 Thread-3 开始上车...
 Thread-4 开始上车...
 车满了，开始出发...
 Thread-5 开始上车...
 Thread-7 开始上车...
 Thread-2 开始上车...
 Thread-6 开始上车...
 车满了，开始出发...
 */