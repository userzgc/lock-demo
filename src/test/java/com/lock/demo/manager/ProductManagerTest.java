package com.lock.demo.manager;
import com.lock.demo.LockDemoApplication;
import com.lock.demo.bean.Product;
import lombok.extern.log4j.Log4j2;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
/**
 * @author zhangguichang
 * @date 2022-07-11 下午8:10
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {LockDemoApplication.class})
@Log4j2
public class ProductManagerTest {
    @Autowired
    private LockManager lockManager;

    //查询商品方法
    @Test
    public void testSQL(){
        Product product = lockManager.queryProductById(1);
        System.out.println(product);
    }
    //无锁更新商品多线程
    @Test
    public void updateProduct(){
        new Thread(()->{
            try {
                String result=lockManager.updateProduct(1);
                log.info("更新结果呢为"+result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        },"t1");
        new Thread(()->{
            try {
                String result=lockManager.updateProduct(1);
                log.info("更新结果呢为"+result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        },"t2");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    //使用synchronized锁住线程资源
    @Test
    public void synUpdateProduct(){
        new Thread(()->{
            try {
                String result=lockManager.synUpdateProduct(1);
                log.info("更新结果呢为"+result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        },"t1");
        new Thread(()->{
            try {
                String result=lockManager.synUpdateProduct(1);
                log.info("更新结果呢为"+result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        },"t2");
    }
    //使用lock实现线程锁
    @Test
    public void lockUpdateProduct(){
        new Thread(()->{
            try {
                String result=lockManager.lockUpdateProduct(1);
                log.info("更新结果呢为"+result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        },"t1");
        new Thread(()->{
            try {
                String result=lockManager.lockUpdateProduct(1);
                log.info("更新结果呢为"+result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        },"t2");

    }

}
