package com.gjw.gjwblog.dao;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gjw.gjwblog.entity.Blog;
import com.gjw.gjwblog.vo.BlogListReturn;
import com.gjw.gjwblog.vo.BlogParam;
import com.gjw.gjwblog.vo.resultmap.BlogEditParamType;
import com.gjw.gjwblog.vo.resultmap.BlogEditResultMap;
import com.gjw.gjwblog.vo.resultmap.BlogHtmlResultMap;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BlogMapper extends BaseMapper<Blog> {

    List<BlogListReturn> listAllBlogReturn(@Param("blogParam") BlogParam blogParam);

    void insertOneBlog(@Param("blog") Blog blog);

    BlogEditResultMap getBlogEditReturnById(@Param("blogId") Integer blogId);

    void updateBlogByBlogId(@Param("blogEditParamType") BlogEditParamType blogEditParamType);

    BlogHtmlResultMap getBlogHtmlById(@Param("blogId") Integer blogId);

    void deleteOneBlog(@Param("blogId") Integer blogId);

    int getBlogCountById(@Param("blogId") Integer blogId);

    void deleteAllBlogs();

    void returnAllBlogs();

    /**
     * 查找数据库中是否有当前博客 如果有博客 则添加失败
     *
     * @param blogTitle
     * @return
     */
    Integer getBlogNumber(@Param("blogTitle") String blogTitle);

    Integer getTypeIdById(@Param("blogId") Integer blogId);

    String getBlogTitleById(@Param("blogId") Integer blogId);

    List<Integer> getTypeIdList();



}
