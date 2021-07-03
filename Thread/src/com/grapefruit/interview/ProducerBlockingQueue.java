/*
 *Copyright @2021 Grapefruit. All rights reserved.
 */

package com.grapefruit.interview;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 使用阻塞队列模拟生产者-消费者交替工作
 *
 * @author zhihuangzhang
 * @version 1.0
 * @date 2021-07-03 5:17 下午
 */
public class ProducerBlockingQueue {
    public static void main(String[] args) throws InterruptedException {

        BlockingQueueSource source = new BlockingQueueSource(new ArrayBlockingQueue<>(10));

        for (int i = 1; i <= 1; i++) {
            new Thread(() -> {
                source.myProd();
            }, "prod").start();
        }

        for (int i = 1; i <= 1; i++) {
            new Thread(() -> {
                source.myConsumer();
            }, "consumer"
            ).start();
        }

        TimeUnit.SECONDS.sleep(5L);
        source.stop();
    }
}


class BlockingQueueSource {
    private volatile boolean flag = true;
    private AtomicInteger atomicInteger = new AtomicInteger();
    private BlockingQueue<String> blockingDeque;

    public BlockingQueueSource(BlockingQueue<String> blockingDeque) {
        this.blockingDeque = blockingDeque;
        System.out.println(blockingDeque.getClass().getName());
    }

    public void stop() {
        flag = false;
    }

    public void myProd() {
        String data;
        boolean retValue;
        while (flag) {
            try {
                atomicInteger.incrementAndGet();
                data = String.valueOf(atomicInteger);
                retValue = blockingDeque.offer(data, 2L, TimeUnit.SECONDS);
                if (retValue) {
                    System.out.println(Thread.currentThread().getName() + "插入数据" + data + " success");
                } else {
                    System.out.println(Thread.currentThread().getName() + "插入数据" + data + " fail");
                }
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("生产叫停----->");
    }

    public void myConsumer() {
        String retData;
        while (flag) {
            try {
                retData = blockingDeque.poll(2L, TimeUnit.SECONDS);

                if (retData == null || "".equals(retData)) {
                    flag = false;
                    System.out.println(Thread.currentThread().getName() + "超过2L无元素 取消消费");
                    System.out.println();
                    System.out.println();
                    System.out.println();
                    return;
                }
                System.out.println(Thread.currentThread().getName() + "消费:" + retData);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("取消消费----->");
    }
}
