package com.gjw.gjwblog.controller;


import com.gjw.gjwblog.result.R;
import com.gjw.gjwblog.service.AdminService;
import com.gjw.gjwblog.vo.AdminParam;
import com.gjw.gjwblog.vo.AdminReturn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class LoginController {


    @Autowired
    private AdminService adminService;


    @PostMapping("login")
    public R login(@RequestBody AdminParam adminParam) {

        String token = adminService.loginOnAndReturnToken(adminParam.getUsername(), adminParam.getPassword());
        if (token != null) {
            // 查找成功 返回一个token token为username
            return R.ok().data("token", token);
        }
        return R.error();
    }

    @GetMapping("info")
    public R info(@RequestParam("token") String token) {

        AdminReturn adminInfo = adminService.returnAdminInfo(token);
        if (adminInfo != null) {
            return R.ok().data("roles", "[管理员]")
                    .data("token", adminInfo.getUsername())
                    .data("avatar", adminInfo.getHeader())
                    .data("name", adminInfo.getName());
        }
        return R.error();
    }
}
