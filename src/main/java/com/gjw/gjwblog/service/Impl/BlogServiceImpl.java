package com.gjw.gjwblog.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gjw.gjwblog.dao.BlogMapper;
import com.gjw.gjwblog.dao.TypeMapper;
import com.gjw.gjwblog.entity.Blog;
import com.gjw.gjwblog.service.BlogService;
import com.gjw.gjwblog.utils.DateUtils;
import com.gjw.gjwblog.utils.FileUtils;
import com.gjw.gjwblog.utils.MarkdownUtils;
import com.gjw.gjwblog.vo.*;
import com.gjw.gjwblog.vo.resultmap.BlogEditParamType;
import com.gjw.gjwblog.vo.resultmap.BlogEditResultMap;
import com.gjw.gjwblog.vo.resultmap.BlogHtmlResultMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service("blogService")
public class BlogServiceImpl extends ServiceImpl<BlogMapper,Blog> implements BlogService {

    @Value("${file.upload.absolutePath}")
    private String absolutePath;

    @Autowired
    private BlogMapper blogMapper;

    @Autowired
    private TypeMapper typeMapper;

    private Lock lock = new ReentrantLock();

    @Override
    public PageInfo<BlogListReturn> findBlogsByParam(BlogParam blogParam) {
        PageHelper.startPage(blogParam.getCurrentPage(), blogParam.getPageSize());
        List<BlogListReturn> blogReturns = blogMapper.listAllBlogReturn(blogParam);
        PageInfo<BlogListReturn> pageInfo = new PageInfo<>(blogReturns);
        return pageInfo;
    }

    /**
     * 保存blog
     *
     * @param blogSaveParam
     */
    @Transactional
    @Override
    public boolean saveOneBlog(BlogSaveParam blogSaveParam) {

        //保存blog 赋值
        /**
         * 提前到数据库查找是否存在此标题的博客
         * 如果number为0则没有 可以插入
         * 如果number为1则不可以插入
         */
        Integer count = blogMapper.getBlogNumber(blogSaveParam.getTitle());
        if (count == 0) {
            Blog blog = new Blog();
            blog.setBlogTitle(blogSaveParam.getTitle());
            blog.setBlogImage(blogSaveParam.getBlogImage());
            blog.setBlogGoods(0);
            blog.setBlogRead(0);
            blog.setBlogCollection(0);
            blog.setBlogType(blogSaveParam.getType());
            blog.setBlogRemark(blogSaveParam.getBlogRemark());
            blog.setBlogComment(0);
            blog.setBlogSource(null);
            blog.setBlogCreateTime(DateUtils.now());
            blog.setBlogModifiedTime(DateUtils.now());
            blog.setBlogVersion(1);
            blog.setIsDeleted(0);
            // 将上传的str字符串存储为md
            String blogContent = blogSaveParam.getBlogContent();
            String blogPath = absolutePath + blogSaveParam.getTitle() + ".md";
            boolean flag = FileUtils.stringToFile(blogContent, blogPath);
            if (flag == true) {
                // 保存成功
                blog.setBlogPath(blogPath);
                lock.lock();
                //保存成功后，并将博客数自增
                blogMapper.insertOneBlog(blog);
                // 博客数增加
                typeMapper.increaseTypeBlogCount(blog.getBlogType());
                lock.unlock();
                return true;
            }
            return false;
        } else {
            //如果number为1则不可以插入
            return false;

        }

    }


    /**
     * 通过blogId查询得到BlogEditResultMap
     * 通过查询到的blogPath读取md文件为字符串
     * 再进行返回
     *
     * @param blogId
     * @return
     */
    @Override
    public BlogEditReturn getBlogEditReturnByBlogId(Integer blogId) throws IOException {

        BlogEditResultMap blogEditResultMap = blogMapper.getBlogEditReturnById(blogId);
        BlogEditReturn blogEditReturn = new BlogEditReturn();
        // 进行赋值
        blogEditReturn.setBlogId(blogEditResultMap.getBlogId());
        blogEditReturn.setBlogTitle(blogEditResultMap.getBlogTitle());
        blogEditReturn.setBlogImage(blogEditResultMap.getBlogImage());
        blogEditReturn.setTypeName(blogEditResultMap.getTypeName());
        blogEditReturn.setBlogRemark(blogEditResultMap.getBlogRemark());
        // 通过blogPath读取md文件 读取为string
        String blogPath = blogEditResultMap.getBlogPath();
        String blogContent = FileUtils.fileToString(blogPath);
        blogEditReturn.setBlogContent(blogContent);
        return blogEditReturn;
    }


    /**
     * 是否可以edit 一个 blog
     *
     * @param blogEditParam
     * @return
     */
    public boolean AbleEditOneBlog(BlogEditParam blogEditParam) {
        // 分类变化了 就进行bl_type表的数据进行修改
        Integer oldTypeId = blogMapper.getTypeIdById(blogEditParam.getId());
        // 先进行编辑修改

        BlogEditParamType blogEditParamType = new BlogEditParamType();
        blogEditParamType.setId(blogEditParam.getId());
        blogEditParamType.setTitle(blogEditParam.getTitle());
        blogEditParamType.setBlogImage(blogEditParam.getBlogImage());
        blogEditParamType.setType(blogEditParam.getType());
        blogEditParamType.setBlogRemark(blogEditParam.getBlogRemark());
        blogEditParamType.setBlogModifiedTime(DateUtils.now());
        // 将上传的str字符串存储为md
        String blogContent = blogEditParam.getBlogContent();
        String blogPath = absolutePath + blogEditParam.getTitle() + ".md";
        boolean flag = FileUtils.stringToFile(blogContent, blogPath);
        if (flag == true) {
            blogEditParamType.setBlogPath(blogPath);
            lock.lock();
            blogMapper.updateBlogByBlogId(blogEditParamType);
            // blog修改完之后，要判断当前bl_type是否需要修改

            if (oldTypeId != blogEditParam.getType()) {
                //如果分类发生变化 就进行bl_type表的数据进行修改
                // 将旧分类 自减
                typeMapper.decreaseTypeBlogCount(oldTypeId);
                // 将新分类 自增
                typeMapper.increaseTypeBlogCount(blogEditParam.getType());
            }
            lock.unlock();
            return true;
        }
        return false;
    }


    /**
     * update blog
     *
     * @param blogEditParam 博客编辑内容
     * @return
     */
    @Transactional
    @Override
    public boolean editOneBlog(BlogEditParam blogEditParam) {

        //判断博客标题是否发生了修改
        String oldBlogTitle = blogMapper.getBlogTitleById(blogEditParam.getId());

        if (!oldBlogTitle.equals(blogEditParam.getTitle())) {
            //当修改了博客标题 需要去数据库查找 是否新的博客标题在数据库中已经存在了
            // 通过上传的博客标题判断当前blog是否可以编辑
            Integer count = blogMapper.getBlogNumber(blogEditParam.getTitle());
            if (count == 0) {
                // count为0可以进行编辑
                return this.AbleEditOneBlog(blogEditParam);

            } else {

                // count为1不可以进行编辑
                return false;
            }
        } else {
            // 标题没有修改 可以直接进行编辑
            return this.AbleEditOneBlog(blogEditParam);
        }

    }

    /**
     * 返回blogHtml
     *
     * @param blogId
     * @return
     */
    @Override
    public BlogHtmlReturn getBlogHtmlReturnById(Integer blogId) throws IOException {

        BlogHtmlReturn blogHtmlReturn = new BlogHtmlReturn();
        BlogHtmlResultMap blogHtmlResultMap = blogMapper.getBlogHtmlById(blogId);
        blogHtmlReturn.setBlogTitle(blogHtmlResultMap.getBlogTitle());
        blogHtmlReturn.setBlogImage(blogHtmlResultMap.getBlogImage());
        blogHtmlReturn.setTypeName(blogHtmlResultMap.getTypeName());
        blogHtmlReturn.setBlogRemark(blogHtmlResultMap.getBlogRemark());
        // 读取md文件转换为html字符串
        String blogHtml = MarkdownUtils.mdToHtml(blogHtmlResultMap.getBlogPath(), null);
        blogHtmlReturn.setBlogHtml(blogHtml);
        return blogHtmlReturn;
    }

    @Transactional
    @Override
    public boolean deleteOneBlog(Integer blogId) {
        // 先进行删除 在进行判断是否删除成功 并将对应bl_type表的博客数自减
        lock.lock();
        blogMapper.deleteOneBlog(blogId);
        // 查询到对应的typeId 然后进行自减
        Integer typeId = blogMapper.getTypeIdById(blogId);
        typeMapper.decreaseTypeBlogCount(typeId);
        lock.unlock();
        int blogCount = blogMapper.getBlogCountById(blogId);
        if (blogCount == 0) {
            // 删除成功
            return true;
        } else {
            return false;
        }
    }

    @Transactional
    @Override
    public boolean deleteAllBlogs() {

        lock.lock();
        blogMapper.deleteAllBlogs();
        // 将bl_type表所有的博客数清空
        typeMapper.clearTypeBlogCount();
        lock.unlock();
        int blogCounts = blogMapper.getBlogCountById(null);
        if (blogCounts == 0) {
            //删除成功
            return true;
        } else {
            //删除失败
            return false;
        }
    }

    @Transactional
    @Override
    public boolean returnAllBlogs() {

        lock.lock();
        blogMapper.returnAllBlogs();
        //还原所有博客 需要将所有的type_blog_count还原
        // 首先需要将分类的博客数清空
        typeMapper.clearTypeBlogCount();
        // 然后获得所有的typeId
        List<Integer> typeIdList = blogMapper.getTypeIdList();
        // 遍历列表 进行自增
        for (Integer typeId : typeIdList) {
            typeMapper.increaseTypeBlogCount(typeId);
        }
        lock.unlock();
        int blogCounts = blogMapper.getBlogCountById(null);
        if (blogCounts != 0) {
            return true;
        } else {
            return false;
        }
    }
}
