package com.gjw.gjwblog;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gjw.gjwblog.dao.BlogMapper;
import com.gjw.gjwblog.dao.UserMapper;
import com.gjw.gjwblog.entity.Blog;
import com.gjw.gjwblog.entity.User;
import com.gjw.gjwblog.utils.MarkdownUtils;
import com.gjw.gjwblog.vo.BlogListDto;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * @author 郭经伟
 * @Date 2021/3/30
 **/

@RunWith(SpringRunner.class)
@SpringBootTest
public class MyBatisPlusTest {

    @Resource
    private UserMapper userMapper;

    @Resource
    private BlogMapper blogMapper;


    @Test
    public void testSelect(){
        System.out.println("---selectAll method test ---");
        List<User> userList=userMapper.selectList(null);
        Assert.assertEquals(5,userList.size());
        userList.forEach(System.out::println);
    }

    @Test
    public void testSelect2(){
        System.out.println("--selectAll method test--");
        List<Blog> blogList = blogMapper.selectList(new QueryWrapper<Blog>().eq("is_deleted", 0));
        blogList.forEach(System.out::println);
    }


    @Test
    public void testSelect3() throws IOException {

        QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
        // 排除  blogSource gmt_create blog_version isDeleted
        queryWrapper.select(Blog.class,fieldInfo -> !fieldInfo.getColumn().equals("blog_source")
                &&!fieldInfo.getColumn().equals("gmt_create")
                &&!fieldInfo.getColumn().equals("blog_version")
                &&!fieldInfo.getColumn().equals("is_deleted"));
        queryWrapper.eq("is_deleted",0);
        List<Blog> blogList = blogMapper.selectList(queryWrapper);
        List<BlogListDto> blogListDtos=new ArrayList<>();
        for(Blog blog : blogList){

            BlogListDto blogListDto = new BlogListDto();
            //读取md文件转换成html
            String blogContent = MarkdownUtils.mdToHtml(blog.getBlogPath(), null);
            BeanUtils.copyProperties(blog,blogListDto);
            blogListDto.setBlogContent(blogContent);
            blogListDtos.add(blogListDto);
        }

        blogListDtos.forEach(System.out::println);
    }

    @Test
    public void testSelect4(){


    }
}
