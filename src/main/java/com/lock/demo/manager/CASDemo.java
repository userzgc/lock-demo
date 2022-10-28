package com.lock.demo.manager;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author zhangguichang
 * @date 2022-10-26 19:24
 */
public class CASDemo {
        // 总访问量
        static int count = 0;

        // 模拟用户访问的方法
        public  static void request() throws InterruptedException {
            // 模拟耗时5毫秒
            TimeUnit.MILLISECONDS.sleep(5);
            synchronized (CASDemo.class){
            // 访问量++
            count ++;// 这里 count 并不是原子的
            }
        }
   /* public  static void request() throws InterruptedException {
        // 模拟耗时5毫秒
        TimeUnit.MILLISECONDS.sleep(5);
        synchronized (CASDemo.class){
            // 访问量++
            count ++;// 这里 count 并不是原子的
        }
    }
*/
        public static void main(String[] args) throws InterruptedException {
            // 开始时间
            long startTime = System.currentTimeMillis();
            // 最大线程数100，模拟100个用户同时访问
            int threadSize = 100;
            //
            CountDownLatch countDownLatch = new CountDownLatch(threadSize);

            for(int i = 0; i < threadSize; i++) {

                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // 模拟用户行为，每个用户访问10次网站
                        try {
                            for(int j = 0; j < 10; j++) {
                                request();
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } finally {
                            countDownLatch.countDown();
                        }
                    }
                });

                thread.start();
            }
            // 怎么保证100个线程结束之后，再执行后面代码？
            countDownLatch.await();
            // 100个线程执行结束时间
            long endTime = System.currentTimeMillis();

            System.out.println(Thread.currentThread().getName() + ",耗时：" + (endTime - startTime) + ", count = " + count);
        }

}
