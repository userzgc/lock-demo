package com.lock.demo.bean;

import lombok.Data;

/**
 * @author zhangguichang
 * @date 2022-10-20 19:20
 */
@Data
public class Blog {
    private int id;
    private String blogTitle;
    private String blogContext;
    private int version;
}
