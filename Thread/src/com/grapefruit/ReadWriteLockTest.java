package com.grapefruit;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author 柚子苦瓜茶
 * @version 1.0
 * @ModifyTime 2020/10/12 19:08:13
 */
public class ReadWriteLockTest {

    static int i;
    public static void main(String[] args) {

        //读写锁
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();


        AtomicInteger k = new AtomicInteger();
        System.out.println("--------------");
        Object obj = new Object();

        for(int j = 1;j<= 10;j++){

            new Thread(() -> {
                synchronized (obj) {
                    System.out.println("===> " + k.getAndIncrement());
                    System.out.println(Thread.currentThread().getName());
                    System.out.println();

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
    public void doSome(Object obj){
        synchronized (obj){
            System.out.println("===> " + i++);
        }
    }
}
