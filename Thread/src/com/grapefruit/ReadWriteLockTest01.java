package com.grapefruit;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author 柚子苦瓜茶
 * @version 1.0
 * @ModifyTime 2020/10/12 19:08:13
 */
public class ReadWriteLockTest01 {

    static int i;
    public static void main(String[] args) {

        //读写锁
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();


        final AtomicInteger[] k = {new AtomicInteger()};
        System.out.println("--------------");
        Object obj = new Object();

        new Thread(() -> {

            try {
                lock.readLock().lock();
                Thread.sleep(1000);

                System.out.println(Thread.currentThread().getName()  + "   " +  System.currentTimeMillis());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.readLock().unlock();
            }
        }).start();

        new Thread(() -> {
            try {
                lock.readLock().lock();
                Thread.sleep(1000);
                System.out.println(Thread.currentThread().getName()  + "   " +  System.currentTimeMillis());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.readLock().unlock();
            }
        }).start();

    }

}
