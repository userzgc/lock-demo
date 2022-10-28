package com.lock.demo.manager;

import org.springframework.stereotype.Service;

/**
 * @author zhangguichang
 * @date 2022-08-01 下午8:24
 */
@Service
public class SynDeadLockManager {
    private static String a = "12";
    private static String b = "34";
   public void deadLock(){
        Thread t1 = new Thread(() -> {
            synchronized (a) {
                System.out.println("t1线程，进入a同步块, 执行中");
                try {
                    Thread.sleep(2000);
                    synchronized (b) {
                        System.out.println("t1线程，进入b同步块，执行中");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "thread1");

        Thread t2 = new Thread(() -> {
            synchronized (b) {
                System.out.println("t2线程，进入b同步块, 执行中");
                synchronized (a) {
                    System.out.println("t2线程， 进入a同步块，执行中");
                }
            }
        }, "thread2");
        t1.start();
        t2.start();
    }
}
