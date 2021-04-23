package com.gjw.gjwblog.controller;


import com.gjw.gjwblog.result.R;
import com.gjw.gjwblog.result.ResultInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {


    @GetMapping("/hello")
    public R hello() {

        String hello = "hello world";
        return R.ok();
    }
}
