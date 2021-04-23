package com.gjw.gjwblog.dao;

import com.gjw.gjwblog.vo.AdminReturn;
import org.apache.ibatis.annotations.Param;

public interface AdminMapper {


    /**
     * 通过username和password查询是否有当前这个admin，并且将username以token进行返回
     *
     * @param username
     * @param password
     * @return
     */
    String findTokenByUserNameAndPassword(@Param("username") String username, @Param("password") String password);

    /**
     * 查找adminInfo
     *
     * @param token
     * @return
     */
    AdminReturn findAdminByToken(@Param("token") String token);

    /**
     * 插入token到Admin
     *
     * @param token
     * @return
     */
    void updateTokenToAdmin(@Param("token") String token, @Param("username") String username);
}
