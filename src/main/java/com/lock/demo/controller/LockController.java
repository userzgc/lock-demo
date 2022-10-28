package com.lock.demo.controller;

import com.lock.demo.bean.Blog;
import com.lock.demo.bean.Product;
import com.lock.demo.manager.BlogManager;
import com.lock.demo.manager.LockManager;
import com.lock.demo.manager.SynDeadLockManager;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author zhangguichang
 * @date 2022-07-11 下午7:28
 */
@RestController
@RequestMapping("/product")
public class LockController {
    @Resource
    private LockManager lockManager;
    @Resource
    private SynDeadLockManager deadLockManager;
    @Resource
    private BlogManager blogManager;

    @GetMapping("/get")
    public Product getUserById(int id){
        return lockManager.queryProductById(id);
    }
    @PostMapping("/syn")
    public String updateProductCount(@RequestBody Product product) throws Exception {
        String s = lockManager.updateProduct(product.getId());
        return s;
    }
    @PostMapping("/lock")
    public String lockProductCount(@RequestBody Product product) throws Exception {
        String s = lockManager.lockUpdateProduct(product.getId());
        return s;
    }
    @PostMapping ("/dead")
    public void deadLock(@RequestBody Product product){
        deadLockManager.deadLock();
    }

    @PostMapping ("/blog/update")
    public void updateBlog(@RequestBody Blog blog){
        blogManager.updateBlogInfo(blog);
    }


}
