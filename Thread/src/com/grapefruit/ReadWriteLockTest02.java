package com.grapefruit;

import java.io.Console;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author 柚子苦瓜茶
 * @version 1.0
 * @ModifyTime 2020/10/12 19:08:13
 */
public class ReadWriteLockTest02 {

    static int i = 0;
    static volatile boolean run = false;
    public static void main(String[] args) {

        //读写锁
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

        CountDownLatch latch = new CountDownLatch(10);
        for(int j = 1;j<= 10;j++){
            if(j % 2  == 0){
                new Thread(() -> {
                    try{
                        //将线程先暂时阻塞在这里,之后一起启动
                        latch.await();
                        long begin = System.currentTimeMillis();
                        lock.writeLock().lock();
                        Thread.sleep(3000);
                        run = false;
                        long end = System.currentTimeMillis();
                        System.out.println("写线程用时:" + (end - begin)/1000 + "秒");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        lock.writeLock().unlock();
                    }
                }).start();
            } else {
                new Thread(()->{
                    //将线程先暂时阻塞在这里,之后一起启动
                    try {
                        latch.await();
                        long begin = System.currentTimeMillis();
                        lock.writeLock().lock();
                        run = false;
                        Thread.sleep(1);
                        long end = System.currentTimeMillis();
                        System.out.println("               读线程用时:" + (end - begin)/1000 + "秒");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }finally {
                        lock.writeLock().unlock();
                    }

                }).start();
            }
            //将之前阻塞的线程释放
            latch.countDown();
        }
    }
}
