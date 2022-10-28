package com.lock.demo.mapper;

import com.lock.demo.bean.Blog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author zhangguichang
 * @date 2022-10-20 19:22
 */
@Mapper
public interface BlogMapper {
    int UpdateBlogByIdAndVersion(Blog blog);

    int queryVersionById(@Param("id") int id);
}
