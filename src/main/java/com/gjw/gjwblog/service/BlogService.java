package com.gjw.gjwblog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.gjw.gjwblog.entity.Blog;
import com.gjw.gjwblog.vo.*;

import java.io.IOException;

public interface BlogService extends IService<Blog> {

    PageInfo<BlogListReturn> findBlogsByParam(BlogParam blogParam);

    boolean saveOneBlog(BlogSaveParam blogSaveParam);

    BlogEditReturn getBlogEditReturnByBlogId(Integer blogId) throws IOException;

    boolean editOneBlog(BlogEditParam blogEditParam);

    BlogHtmlReturn getBlogHtmlReturnById(Integer blogId) throws IOException;

    boolean deleteOneBlog(Integer blogId);

    boolean deleteAllBlogs();

    boolean returnAllBlogs();


}
