package com.gjw.gjwblog.entity;


import lombok.Data;

@Data
public class Admin {

    private String id;
    private String name;
    /**
     * 头像地址
     */
    private String header;
    /**
     * 个性签名
     */
    private String signature;

    /**
     * 个人介绍
     */
    private String introduce;

    private String username;

    private String password;
}
