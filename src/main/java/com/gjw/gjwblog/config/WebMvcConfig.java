package com.gjw.gjwblog.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 配制全局跨域
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {


    private static final Logger logger = LoggerFactory.getLogger(WebMvcConfig.class);

    @Value("${file.upload.absolutePath}")
    private String absolutePath;

    @Value("${file.upload.urlPath}")
    private String urlPath;

    /**
     * 配置跨域
     *
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {

        registry.addMapping("/**")
                .allowedOrigins("http://localhost:9999", "null")
                .allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE");
               /* .maxAge(3600)
                .allowCredentials(true);*/
    }

    /**
     * 配置静态资源映射
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        //markdown文件位置建立映射
        logger.info(urlPath);
        logger.info(absolutePath);
        registry.addResourceHandler(urlPath).addResourceLocations("file:" + absolutePath);
    }
}
