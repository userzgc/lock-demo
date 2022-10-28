package com.lock.demo.manager;

import com.lock.demo.LockDemoApplication;
import com.lock.demo.bean.Blog;
import org.junit.Test;
import org.junit.runner.Description;
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
public class CASManagerTest {
    @Resource
    private CASLockManager casLockManager;
    @Test()
    public void addCountTest(){
        casLockManager.addCount();
    }

    @Test()
    public void casAtomicCountTest() {
        casLockManager.CASAtomicCount();
    }
}
