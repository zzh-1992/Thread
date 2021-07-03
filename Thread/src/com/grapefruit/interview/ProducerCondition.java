/*
 *Copyright @2021 Grapefruit. All rights reserved.
 */

package com.grapefruit.interview;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 生产者与消费者
 *
 * @author zhihuangzhang
 * @version 1.0
 * @date 2021-07-03 11:44 上午
 */
public class ProducerCondition {
    public static void main(String[] args) {
        Source source = new Source(0);
        for (int i = 1; i <= 5; i++) {
            new Thread(() -> {
                while (true) {
                    source.decrement();
                }
            }).start();
        }

        for (int i = 1; i <= 5; i++) {
            new Thread(() -> {
                while (true) {
                    source.increment();
                }
            }).start();
        }
    }
}


class Source {
    ReentrantLock lock = new ReentrantLock();
    int num;
    Condition condition = lock.newCondition();

    public Source(int num) {
        this.num = num;
    }

    public void increment() {
        // 生产者
        lock.lock();
        try {
            while (num != 0) {
                try {
                    // 有资源的时候生产者先等待
                    condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            // 生产
            num++;
            System.out.println(Thread.currentThread().getName() + "生产" + num);

            // 唤醒其他线程
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public void decrement() {
        //消费者
        lock.lock();
        try {
            while (num == 0) {
                try {
                    condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            num--;
            System.out.println(Thread.currentThread().getName() + "消费" + num);

            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }
}
