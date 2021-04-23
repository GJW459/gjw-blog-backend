package com.gjw.gjwblog.controller;

import com.github.pagehelper.PageInfo;
import com.gjw.gjwblog.result.R;
import com.gjw.gjwblog.service.BlogService;
import com.gjw.gjwblog.utils.BlogUtils;
import com.gjw.gjwblog.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/**
 * @author gjw
 * 主要是对于Blog的查询
 */
@RestController
@RequestMapping("/blog")
public class BlogController {


    @Autowired
    private BlogService blogService;

    /**
     * 条件查询Blog
     *
     * @param blogParam
     * @return
     */
    @PostMapping("list")
    public R getBlogByCondition(@RequestBody BlogParam blogParam) {

        BlogParam preModifyBlogParam = BlogUtils.preModifyBlogParam(blogParam);
        String begin = preModifyBlogParam.getBegin();
        String end = preModifyBlogParam.getEnd();
        int flag = end.compareTo(begin);// flag=-1 end<begin
        if (flag == -1) {
            // 格式错误 end不能大于begin
            return R.error();
        }
        PageInfo<BlogListReturn> blogsByParam = blogService.findBlogsByParam(preModifyBlogParam);
        if (blogsByParam != null) {
            return R.ok().data("blogs", blogsByParam);
        }
        return R.error();
    }

    /**
     * 保存blog
     *
     * @param blogSaveParam
     * @return
     */
    @PostMapping("save")
    public R uploadBlog(@RequestBody BlogSaveParam blogSaveParam) {

        boolean flag = blogService.saveOneBlog(blogSaveParam);
        if (flag == true) {
            // 存储成功
            return R.ok();
        } else {
            // 失败
            return R.error().data("message", "保存博客失败");
        }
    }

    /**
     * 获取blogEditReturn
     *
     * @return
     */
    @GetMapping("blogEditReturn")
    public R getBlogEditReturn(@RequestParam("blogId") Integer blogId) {

        BlogEditReturn blogEditReturn = null;
        try {
            blogEditReturn = blogService.getBlogEditReturnByBlogId(blogId);
            return R.ok().data("blogEditReturn", blogEditReturn);
        } catch (IOException e) {
            e.printStackTrace();
            return R.error();
        }
    }

    /**
     * edit blog
     *
     * @param blogEditParam
     * @return
     */
    @PostMapping("blogEdit")
    public R editBlog(@RequestBody BlogEditParam blogEditParam) {

        boolean flag = blogService.editOneBlog(blogEditParam);
        if (flag == true) {
            // 更新成功
            return R.ok();
        } else {
            // 失败
            return R.error();
        }
    }

    /**
     * 返回blogHtml
     *
     * @param blogId
     * @return
     */
    @GetMapping("blogHtml")
    public R getBlogHtml(@RequestParam("blogId") Integer blogId) {

        try {
            BlogHtmlReturn blogHtmlReturn = blogService.getBlogHtmlReturnById(blogId);
            return R.ok().data("blogHtmlReturn", blogHtmlReturn);
        } catch (IOException e) {
            e.printStackTrace();
            return R.error();
        }
    }

    /**
     * 删除指定的blog
     *
     * @param blogId
     * @return
     */
    @GetMapping("deleteBlog")
    public R deleteOneBlog(@RequestParam("blogId") Integer blogId) {
        boolean isDeleted = blogService.deleteOneBlog(blogId);
        if (isDeleted) {
            return R.ok().data("message", "删除成功");
        } else {
            return R.error();
        }
    }

    /**
     * 删除所有blog
     *
     * @return
     */
    @GetMapping("deleteAllBlogs")
    public R deleteAllBlogs() {
        boolean isAllDeleted = blogService.deleteAllBlogs();
        if (isAllDeleted) {
            return R.ok().data("message", "删除成功");
        } else {
            return R.error();
        }
    }

    /**
     * 还原所有删除的blog
     *
     * @return
     */
    @GetMapping("returnAllBlogs")
    public R returnAllBlogs() {
        boolean isAllReturn = blogService.returnAllBlogs();
        if (isAllReturn) {
            return R.ok().data("message", "还原所有blog成功");
        } else {
            return R.error();
        }
    }
}
