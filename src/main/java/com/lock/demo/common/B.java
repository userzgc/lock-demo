package com.lock.demo.common;

import org.openjdk.jol.info.ClassLayout;

/**
 * @author zhangguichang
 * @date 2022-11-01 14:26
 */
public class B {
    static A a;

    public static void main(String[] args) throws InterruptedException {
        System.out.println(Thread.currentThread().getName());
        Thread.sleep(5000);
        a=new A();
        System.out.println("befor lock");
        System.out.println(ClassLayout.parseInstance(a).toPrintable());
        synchronized (a){
            System.out.println("lock ing");
            System.out.println(ClassLayout.parseInstance(a).toPrintable());
        }
        System.out.println("after lock");
        System.out.println(ClassLayout.parseInstance(a).toPrintable());
    }
}
