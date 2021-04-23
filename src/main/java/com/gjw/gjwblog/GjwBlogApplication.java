package com.gjw.gjwblog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.gjw.gjwblog.dao")
public class GjwBlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(GjwBlogApplication.class, args);
    }

}
