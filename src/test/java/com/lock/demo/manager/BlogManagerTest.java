package com.lock.demo.manager;

import com.lock.demo.LockDemoApplication;
import com.lock.demo.bean.Blog;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author zhangguichang
 * @date 2022-10-24 16:58
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {LockDemoApplication.class})
public class BlogManagerTest {
    @Resource
    private BlogManager blogManager;
    @Test
    public void updateBlogTest() throws InterruptedException {
        Blog blog=new Blog();
        blog.setId(1);
        blog.setBlogTitle("hello");
        Thread t1=new Thread(()->{
            blogManager.updateBlogInfo(blog);
        },"t1");
        Thread t2=new Thread(()->{
            blogManager.updateBlogInfo(blog);
        },"t2");
        t1.start();
        t2.start();
        Thread.sleep(10000);
    }
}
