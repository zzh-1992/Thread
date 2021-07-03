/*
 *Copyright @2021 Grapefruit. All rights reserved.
 */

package com.grapefruit.interview;

import java.util.concurrent.locks.ReentrantLock;

/**
 * synchronized与ReentrantLock的区别
 *
 * @author zhihuangzhang
 * @version 1.0
 * @date 2021-07-03 2:54 下午
 */
public class t {
    public static void main(String[] args) {
        synchronized (new Object()){

        }
        ReentrantLock lock = new ReentrantLock();
    }
}
