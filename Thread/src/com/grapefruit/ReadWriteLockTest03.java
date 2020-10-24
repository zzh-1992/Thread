package com.grapefruit;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author 柚子苦瓜茶
 * @version 1.0
 * @ModifyTime 2020/10/12 19:08:13
 */
public class ReadWriteLockTest03 {

    static int i = 0;
    public static void main(String[] args) {

        //读写锁
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

        ExecutorService executorService = Executors.newFixedThreadPool(10);

        executorService.execute(new Thread(() -> {
            if(i%2 == 0){
                try{
                    long begin = System.currentTimeMillis();
                    lock.writeLock().lock();
                    Thread.sleep(2000);
                    i++;
                    long end = System.currentTimeMillis();
                    System.out.println("写线程用时:" + (end - begin) + "秒");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.writeLock().unlock();
                }
            }else {
                long begin = System.currentTimeMillis();
                System.out.println(i++);
                long end = System.currentTimeMillis();
                System.out.println("               读线程用时:" + (end - begin) + "秒");

            }

        }));
        for(int j = 1;j<= 10;j++){
            new Thread(() -> {
                if(i%2 == 0){
                    try{
                        long begin = System.currentTimeMillis();
                        lock.writeLock().lock();
                        Thread.sleep(2000);
                        i++;
                        long end = System.currentTimeMillis();
                        System.out.println("写线程用时:" + (end - begin) + "秒");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        lock.writeLock().unlock();
                    }
                }else {
                    long begin = System.currentTimeMillis();
                    System.out.println(i++);
                    long end = System.currentTimeMillis();
                    System.out.println("               读线程用时:" + (end - begin) + "秒");

                }

            }).start();
        }


        //System.out.println("用时:" + (end - begin) + "秒");
    }
}
