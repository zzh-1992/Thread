/*
 *Copyright @2021 Grapefruit. All rights reserved.
 */

package com.grapefruit.interview;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 相关描述
 *
 * @author zhihuangzhang
 * @version 1.0
 * @date 2021-07-03 10:00 上午
 */
public class CyclicBarrierTest {

    public static void main(String[] args) {
        java.util.concurrent.CyclicBarrier cyclicBarrier = new java.util.concurrent.CyclicBarrier(7, () -> System.out.println("-----> 召唤神龙"));

        ThreadPoolExecutor executor = new ThreadPoolExecutor(7, 7, 30L,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(7),
                Executors.defaultThreadFactory());

        for (int i = 1; i <= 7; i++) {
            int finalI = i;
            executor.submit(() -> {
                try {
                    System.out.println("收集第" + finalI + "颗龙珠");
                    cyclicBarrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            });
        }

        /*for(int i = 1;i<=7;i++){
            int finalI = i;
            new Thread(()->{
                try {
                    System.out.println("收集第" + finalI + "颗龙珠");
                    cyclicBarrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }).start();
        }*/
        executor.shutdown();
    }
}
