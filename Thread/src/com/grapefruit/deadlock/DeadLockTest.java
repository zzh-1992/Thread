/*
 *Copyright @2021 Grapefruit. All rights reserved.
 */

package com.grapefruit.deadlock;

import java.util.concurrent.TimeUnit;

/**
 * 死锁测试 DeadLockTest
 *
 * @author zhihuangzhang
 * @version 1.0
 * @date 2021-07-04 8:09 上午
 */
public class DeadLockTest {

    private static final String l1 = "a";
    private static final String l2 = "b";

    public static void main(String[] args) {
        new Thread(() -> {
            m1();
        }, "T1").start();
        new Thread(() -> {
            m2();
        }, "T2").start();
    }

    public static void m1() {
        System.out.println("m2尝试获取m1");
        synchronized (l1) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (l2) {

            }
        }
    }

    public static void m2() {
        System.out.println("m2尝试获取m1");
        synchronized (l2) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (l1) {

            }
        }
    }
}
