package com.gjw.gjwblog.service;


import com.gjw.gjwblog.vo.AdminReturn;

public interface AdminService {


    String loginOnAndReturnToken(String username, String password);

    AdminReturn returnAdminInfo(String token);
}
