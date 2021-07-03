/*
 *Copyright @2021 Grapefruit. All rights reserved.
 */

package com.grapefruit.interview;

import java.util.concurrent.Semaphore;

/**
 * (n个资源被m个线程抢占)
 *
 * @author zhihuangzhang
 * @version 1.0
 * @date 2021-07-03 10:41 上午
 */
public class SemaphoreTest {

    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(3);
        for (int i = 1; i <= 60; i++) {
            int finalI = i;
            new Thread(() -> {
                try {
                    semaphore.acquire();
                    System.out.println("线程:" + finalI + "=====>");
                    Thread.sleep(3000);
                    System.out.println("线程:" + finalI + "---leave");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    semaphore.release();
                }
            }).start();
        }
    }
}
