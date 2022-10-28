package com.lock.demo.bean;

import lombok.Data;

/**
 * @author zhangguichang
 * @date 2022-07-11 下午7:27
 */
@Data
public class Product {
    private int id;
    private String productName;
    private String category;
    private int productCount;

}
