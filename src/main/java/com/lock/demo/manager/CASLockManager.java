package com.lock.demo.manager;

import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author zhangguichang
 * @date 2022-10-18 19:57
 */
@Service
public class CASLockManager {
    /*
    count++做的事情，首先这个count是静态变量，静态变量存储在堆内存中
    1.第一步方法访问count变量，栈内存中复制一份数据并生成一个变量A=count
    2.第二步B=A+1
    3.第三步:将栈内的数据同步至堆内存的Count变量中，count=B,
    如果是不加锁的情况下，这个count会被多个线程引用，比如线程A走到第三步了，但是线程B才走到第一步。导致count最后一定是小于200
*/
    private static int count = 0;
    private static AtomicInteger integer = new AtomicInteger(0);
    private static int firstCount = 0;
    //使用原子类实现CAS
    public void CASAtomicCount(){
        for (int i = 0; i < 2; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        firstCount++;
                        System.out.println(firstCount);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    //每个线程让count自增100次
                    for (int i = 0; i < 100; i++) {
                        integer.incrementAndGet();
                    }
                }
            }).start();
        }

        try{
            Thread.sleep(2000);
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println(integer);
    }

    //线程不安全的count++
    public void addCount(){
        for (int i = 0; i < 2; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        firstCount++;
                        System.out.println(firstCount);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    //每个线程让count自增100次
                    for (int i = 0; i < 100; i++) {
                        count++;
                    }
                }
            }).start();
        }

        try{
            Thread.sleep(2000);
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println(integer);
    }

}
