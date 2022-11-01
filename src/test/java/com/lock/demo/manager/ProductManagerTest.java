package com.lock.demo.manager;
import com.lock.demo.LockDemoApplication;
import com.lock.demo.bean.Product;
import com.lock.demo.common.A;
import lombok.extern.log4j.Log4j2;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openjdk.jol.info.ClassLayout;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.concurrent.CountDownLatch;

/**
 * @author zhangguichang
 * @date 2022-07-11 下午8:10
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {LockDemoApplication.class})
@Log4j2
public class ProductManagerTest {
    @Resource
    private LockManager lockManager;
    CountDownLatch countDownLatch = new CountDownLatch(2);
    @Test
    public void testLockLevel(){


    }


    //查询商品方法
    @Test
    public void testSQL(){
        Product product = lockManager.queryProductById(1);
        System.out.println(product);
    }

    @Test
    public void rollBackProductCount(){
        lockManager.rollBackProductCount(1);;
    }

    //无锁更新商品多线程
    @Test
    public void updateProduct() throws InterruptedException {
        Thread t1=new Thread(()->{
            try {
                String result=lockManager.updateProduct(1);
                log.info("更新结果呢为"+result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        },"t1");
        Thread t2=new Thread(()->{
            try {
                String result=lockManager.updateProduct(1);
                log.info("更新结果呢为"+result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        },"t2");
        try {
            t1.start();
            t2.start();
        }finally {
            countDownLatch.countDown();
        }
        countDownLatch.await();
    }
    //使用synchronized锁住线程资源
    @Test
    public void synUpdateProduct() throws InterruptedException {
        Thread t1=new Thread(()->{
            try {
                 String result=lockManager.synUpdateProduct(1);
                log.info("更新结果呢为"+result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        },"t1");
        Thread t2=new Thread(()->{
            try {
                String result=lockManager.synUpdateProduct(1);
                log.info("更新结果呢为"+result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        },"t2");
        try {
            t1.start();
            t2.start();
        }finally {
            countDownLatch.countDown();
        }
        countDownLatch.await();

    }
    //使用ReentrantLock实现线程锁
    @Test
    public void lockUpdateProduct() throws InterruptedException {
        LockManager lo=new LockManager();
        System.out.println("-----------------------------------------------");
        log.info("hashcode:"+Integer.toHexString(lo.hashCode()));
        log.info(ClassLayout.parseInstance(lo).toPrintable());
       Thread t1= new Thread(()->{
            try {
                String result=lockManager.lockUpdateProduct(1);
                log.info("更新结果呢为"+result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        },"t1");
       Thread t2= new Thread(()->{
            try {
                String result=lockManager.lockUpdateProduct(1);
                log.info("更新结果呢为"+result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        },"t2");
        try {
            t1.start();
            t2.start();
        }finally {
            countDownLatch.countDown();
        }
        countDownLatch.await();
    }


}
