package com.lsk.juc;

import java.util.concurrent.TimeUnit;

/**
 * 1验证volatile的可见性
 * 1.1 如果int num = 0，number变量没有添加volatile关键字修饰
 * 1.2 添加了volatile，可以解决可见性
 */
public class VolatileDemo {

    public static void main(String[] args) {
        visibilityByVolatile();//验证volatile的可见性
    }

    /**
     * volatile可以保证可见性，及时通知其他线程，主物理内存的值已经被修改
     */
    public static void visibilityByVolatile() {
        MyData myData = new MyData();

        //第一个线程
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t come in");
            try {
                TimeUnit.SECONDS.sleep(3); //线程暂停3s
                myData.addToSixty();
                System.out.println(Thread.currentThread().getName() + "\t update value:" + myData.num);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "thread1").start();

        //第二个线程是main线程
        while (myData.num==0){
            //如果myData的num一直为零，main线程一直在这里循环
        }
        System.out.println(Thread.currentThread().getName() + "\t mission is over, num value is " + myData.num);
    }
}

class MyData {
    //    int num = 0;
    volatile int num = 0;

    public void addToSixty() {
        this.num = 60;
    }
}

/**
 int num = 0;时
 thread1	 come in
 thread1	 update value:60
 //线程进入死循环



 volatile int num = 0;时
 thread1	 come in
 thread1	 update value:60
 main	 mission is over, num value is 60
 //程序没有死循环，结束执行


 */
