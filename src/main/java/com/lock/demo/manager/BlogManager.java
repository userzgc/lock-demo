package com.lock.demo.manager;

import com.lock.demo.bean.Blog;
import com.lock.demo.mapper.BlogMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zhangguichang
 * @date 2022-10-24 14:49
 */
@Service
@Log4j2
public class BlogManager {
  @Autowired
  private BlogMapper blogMapper;
  /*根据mysql version字段来判断数据是否被其他线程执行*/
  public void  updateBlogInfo(Blog blog){
        int version = blogMapper.queryVersionById(blog.getId());
        blog.setVersion(version);
        try {
          Thread.sleep(1000);
          if (blogMapper.UpdateBlogByIdAndVersion(blog)>0){
            log.info("当前修改成功线程名字"+Thread.currentThread().getName());
            log.info("修改成功");
          }else {
            log.info("当前修改失败线程名字"+Thread.currentThread().getName());
            log.warn("修改失败");
          }

        }catch (Exception e){
          log.error(e.getMessage());
        }

  }
}
