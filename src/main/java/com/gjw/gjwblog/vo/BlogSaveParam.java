package com.gjw.gjwblog.vo;


import lombok.Data;

@Data
public class BlogSaveParam {

    private String title;
    private Integer type;
    private String blogImage;
    private String blogContent;
    /**
     * 博客备注
     */
    private String blogRemark;
}
