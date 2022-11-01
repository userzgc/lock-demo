package com.lock.demo.manager;

import com.lock.demo.bean.Product;
import com.lock.demo.mapper.ProductMapper;
import lombok.extern.log4j.Log4j2;
import org.openjdk.jol.info.ClassLayout;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author zhangguichang
 * @date 2022-07-11 下午7:29
 */
@Service
@Log4j2
public class LockManager {
    @Resource
    private ProductMapper productMapper;
    private ReentrantLock lock=new ReentrantLock();

    //查找商品信息
    public Product queryProductById(int id){
        return productMapper.queryProductById(id);
    }

    public void rollBackProductCount(int id){
            if (productMapper.updateProductCountById(id)>0){
                log.info("库存回滚成功");
            }
            else {
                log.error("回滚失败");
            }
    }

    //无锁更新商品
    public String updateProduct(int id) throws Exception {
        System.out.println("开始执行");

        Product product=new Product();
        Product productInfo = productMapper.queryProductById(id);
        Thread.sleep(1000);
            if(productInfo.getProductCount()>=1){
                product.setId(id);
                product.setProductCount(productInfo.getProductCount()-1);
                productMapper.updateProductCount(product);
            } else {

                    throw new Exception("库存不足");
            }
        System.out.println("执行结束");
            return "商品购买结果:"+true;
    }

    //排它锁更新库存
    public synchronized String synUpdateProduct(int id) throws Exception {
        log.info("hashcode:"+LockManager.class.hashCode());
        System.out.println(ClassLayout.parseInstance(LockManager.class).toPrintable());
        System.out.println("开始执行");
        Product product=new Product();
        Product productInfo = productMapper.queryProductById(id);
        if(productInfo.getProductCount()>=1){
            product.setId(id);
            product.setProductCount(productInfo.getProductCount()-1);
            productMapper.updateProductCount(product);
        } else {

            throw new Exception("库存不足");
        }
        System.out.println("执行结束");
        return "商品购买结果:"+true;
    }

    //手动加lock实现排它锁
    public String lockUpdateProduct(int id) throws Exception {
//        Lock lock=new ReentrantLock();
//        Thread.sleep(1000);
        lock.lock();
        System.out.println("获取当前线程"+Thread.currentThread().getName());

        Product product=new Product();
        try {
            Product productInfo = productMapper.queryProductById(id);
            System.out.println(Thread.currentThread().getName()+"获取库存"+productInfo.getProductCount()+"");
            if(productInfo.getProductCount()>=1){
                product.setId(id);
                product.setProductCount(productInfo.getProductCount()-1);
                productMapper.updateProductCount(product);

            } else {
                throw new Exception("库存不足");
            }
        }finally {
            lock.unlock();
        }
        System.out.println("执行结束");
        return "商品购买结果:"+true;
    }


}
