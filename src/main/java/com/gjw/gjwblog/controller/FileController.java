package com.gjw.gjwblog.controller;

import com.gjw.gjwblog.result.R;
import com.gjw.gjwblog.utils.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;

@RestController
public class FileController {

    @Value("${file.upload.absolutePath}")
    private String absolutePath;

    @Value("${file.upload.mdImageDir}")
    private String mdImageDir;


    /**
     * MultipartFile和request都一样
     * 都是被SpringMVC帮我们自动封装的
     *
     * @param image
     * @param request
     * @return
     */
    @PostMapping("/image/upload")
    public R uploadImage(MultipartFile image, HttpServletRequest request) {

        /**
         * 主要的步骤就是
         * 1.保存的路径是否存在，如果不存在就创建
         * 2.将图片保存在服务器文件系统中
         * 3.将图片映射的路径返回给前端
         */
        // 文件上传保存的文件夹
        String filePath = mdImageDir;
        // 设置返回给前端的URL http://localhost:8080/assets/...
        StringBuffer url = new StringBuffer();
        url.append(request.getScheme())
                .append("://")
                .append(request.getServerName())
                .append(":")
                .append(request.getServerPort())
                .append(request.getContextPath())
                .append("/note/assets");
        // 将上传的文件保存到本地
        File uploadImage = FileUtils.upload(image, filePath);
        if (uploadImage != null) {
            url.append("/" + uploadImage.getName());
            return R.ok().data("url", url);
        } else {
            return R.error();
        }


    }
}
