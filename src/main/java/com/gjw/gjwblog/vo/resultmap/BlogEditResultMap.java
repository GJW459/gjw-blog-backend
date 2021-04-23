package com.gjw.gjwblog.vo.resultmap;


import lombok.Data;

@Data
public class BlogEditResultMap {

    private Integer blogId;
    private String blogTitle;
    private String typeName;
    private String blogImage;
    private String blogPath;
    private String blogRemark;
}
