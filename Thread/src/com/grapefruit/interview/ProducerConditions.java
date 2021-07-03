/*
 *Copyright @2021 Grapefruit. All rights reserved.
 */

package com.grapefruit.interview;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 生产者与消费者
 *
 * @author zhihuangzhang
 * @version 1.0
 * @date 2021-07-03 11:44 上午
 */
public class ProducerConditions {
    public static void main(String[] args) {
        Sources source = new Sources();
        /*for (int i = 1; i <= 5; i++) {
            new Thread(() -> {
                source.print1();
            }, "A").start();

            new Thread(() -> {
                source.print2();
            }, "B").start();

            new Thread(() -> {
                source.print4();
            }, "C").start();
        }*/

        for (int i = 1; i <= 5; i++) {
            new Thread(() -> {
                source.print(1);
            }, "A").start();

            new Thread(() -> {
                source.print(2);
            }, "B").start();

            new Thread(() -> {
                source.print(3);
            }, "C").start();
        }
    }
}

class Sources {
    ReentrantLock lock = new ReentrantLock();
    int num = 1;
    Condition c1 = lock.newCondition();
    Condition c2 = lock.newCondition();
    Condition c3 = lock.newCondition();
    Map<Integer,MyNode> map;
    {
        map = new HashMap<>();
        MyNode a = new MyNode(null,null,1,c1);
        MyNode b = new MyNode(a,null,2,c2);
        MyNode c = new MyNode(b,a,3,c3);

        a.setAfter(b);
        a.setPre(c);
        b.setAfter(c);

        map.put(1,a);
        map.put(2,b);
        map.put(3,c);
    }

    public void print(int j) {
        lock.lock();
        try {
            // 1 判断
            while (num != j) {
                map.get(j).getCondition().await();
            }
            for (int i = 1; i <= map.get(j).getI(); i++) {
                // 2 干活
                System.out.println(Thread.currentThread().getName());
            }
            // 3 唤醒
            num = map.get(j).getAfter().getI();
            map.get(j).getAfter().getCondition().signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void print1() {
        lock.lock();
        try {
            // 1 判断
            while (num != 1) {
                c1.await();
            }
            for (int i = 1; i <= 1; i++) {
                // 2 干活
                System.out.println(Thread.currentThread().getName() + "A-");
            }
            // 3 唤醒
            num = 2;
            c2.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void print2() {
        lock.lock();
        try {
            // 1 判断
            while (num != 2) {
                c2.await();
            }
            for (int i = 1; i <= 2; i++) {
                // 2 干活
                System.out.println(Thread.currentThread().getName() + "B-");
            }
            // 3 唤醒
            num = 3;
            c3.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void print4() {
        lock.lock();
        try {
            // 1 判断
            while (num != 3) {
                c3.await();
            }
            for (int i = 1; i <= 4; i++) {
                // 2 干活
                System.out.println(Thread.currentThread().getName() + "C-");
            }
            // 3 唤醒
            num = 1;
            c1.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}

@Data
@AllArgsConstructor
@Setter
class MyNode{
    MyNode pre;
    MyNode after;
    int i;
    Condition condition;
}
