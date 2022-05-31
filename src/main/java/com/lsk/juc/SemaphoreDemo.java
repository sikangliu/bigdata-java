package com.lsk.juc;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class SemaphoreDemo {

    public static void main(String[] args) {
        //当只有一个资源的时候，等同于synchronized
        Semaphore semaphore = new Semaphore(3);//模拟3个停车位

        for (int i = 1; i <= 6; i++) { //模拟6辆车
            new Thread(() -> {
                try {
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName() + "\t 抢到车位");
                    TimeUnit.SECONDS.sleep(new Random().nextInt(5));
                    System.out.println(Thread.currentThread().getName() + "\t 离开");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    semaphore.release();
                }
            }, String.valueOf(i)).start();
        }
    }
}

/**
 1	 抢到车位
 2	 抢到车位
 3	 抢到车位
 3	 离开
 2	 离开
 4	 抢到车位
 5	 抢到车位
 5	 离开
 6	 抢到车位
 1	 离开
 6	 离开
 4	 离开
 */