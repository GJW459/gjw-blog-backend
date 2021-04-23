package com.gjw.gjwblog.vo.resultmap;


import lombok.Data;

@Data
public class BlogEditParamType {


    private Integer id;
    private String title;
    private Integer type;
    private String blogImage;
    private String blogPath;
    /**
     * 博客备注
     */
    private String blogRemark;

    private String blogModifiedTime;
}
