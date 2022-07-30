/*
 *Copyright @2022 Grapefruit. All rights reserved.
 */

package com.grapefruit.interview;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * CountDownLatch(所有子线程同时运行)
 *
 * @Author ZhangZhihuang
 * @Date 2022/7/30 13:25
 * @Version 1.0
 */
public class CountDownLatchTest {

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        // 线程池
        ExecutorService service = Executors.newFixedThreadPool(5);

        // 定义5个子线程
        for (int i = 0; i < 5; i++) {
            service.execute(() -> {
                try {
                    // 子线程等待
                    latch.await();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                // 子线程输出信息
                System.out.println("name:" + Thread.currentThread().getName() + " time:" + System.currentTimeMillis());
            });
        }

        // 主线程输出信息
        System.out.println("main thread:" + Thread.currentThread().getName() + " time:" + System.currentTimeMillis());

        // 主线程休眠1秒
        Thread.sleep(1000);

        // 主线程让lathc的个数减1,之后所有子线程同时执行
        latch.countDown();

        // 关闭线程池
        service.shutdown();
    }
}
