package com.gjw.gjwblog.vo;

import lombok.Data;

/**
 * blog返回实体 /blog/list 实体
 */
@Data
public class BlogListReturn {

    private Integer blogId;
    private String blogTitle;
    private String typeName;
    private String blogImage;
    private Integer blogGoods;
    private Integer blogRead;
    private Integer blogCollection;
    private String blogComment;
    private String blogSource;
    private String blogCreateTime;
    private String blogModifiedTime;
    private String blogRemark;
}
