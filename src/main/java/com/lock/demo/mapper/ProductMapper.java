package com.lock.demo.mapper;

import com.lock.demo.bean.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


/**
 * @author zhangguichang
 * @date 2022-07-11 下午7:36
 */
@Mapper
public interface ProductMapper {
     Product queryProductById(@Param("id") int id);
     int updateProductCount(Product product);
}
