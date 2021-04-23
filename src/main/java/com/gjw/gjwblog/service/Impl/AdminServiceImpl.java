package com.gjw.gjwblog.service.Impl;

import com.gjw.gjwblog.dao.AdminMapper;
import com.gjw.gjwblog.service.AdminService;
import com.gjw.gjwblog.vo.AdminReturn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.UUID;


@Service("adminService")
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    /**
     * 登录并且返回给前端token
     *
     * @param username
     * @param password
     * @return
     */
    @Transactional
    @Override
    public String loginOnAndReturnToken(String username, String password) {
        String token = adminMapper.findTokenByUserNameAndPassword(username, password);
        if (!StringUtils.isEmpty(token)) {
            //不为空 返回 token
            token = UUID.randomUUID().toString().replace(".", "_") + token;
            //token 插入到bl_admin中
            adminMapper.updateTokenToAdmin(token, username);
            return token;
        } else {
            return null;
        }

    }

    @Override
    public AdminReturn returnAdminInfo(String token) {
        return adminMapper.findAdminByToken(token);
    }
}
