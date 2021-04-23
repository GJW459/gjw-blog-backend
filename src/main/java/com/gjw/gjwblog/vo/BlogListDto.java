package com.gjw.gjwblog.vo;

import lombok.Data;

/**
 * @author 郭经伟
 * @Date 2021/3/29
 * <p>
 * 博客列表的数据传输对象
 **/
@Data
public class BlogListDto {

    private Integer blogId;
    private String blogTitle;
    private String blogImage;
    private String blogContent;
    private Integer blogGoods;
    private Integer blogRead;
    private Integer blogCollection;
    private Integer blogType;
    private String blogRemark;
    private Integer blogComment;
    private String blogModifiedTime;
}
