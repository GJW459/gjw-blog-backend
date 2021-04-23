package com.gjw.gjwblog.vo;


import lombok.Data;

@Data
public class BlogEditReturn {

    private Integer blogId;
    private String blogTitle;
    private String typeName;
    private String blogImage;
    private String blogContent;
    private String blogRemark;
}
