package com.gjw.gjwblog.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;


@Data
@TableName(value = "bl_blog")
public class Blog {

    @TableId(value = "blog_id")
    private Integer blogId;
    @TableField(value = "blog_title")
    private String blogTitle;
    @TableField(value = "blog_image")
    private String blogImage;
    @TableField(value = "blog_path")
    private String blogPath;
    @TableField(value = "blog_goods")
    private Integer blogGoods;
    @TableField(value = "blog_read")
    private Integer blogRead;
    @TableField(value = "blog_collection")
    private Integer blogCollection;
    @TableField(value = "blog_type")
    private Integer blogType;
    @TableField(value = "blog_remark")
    private String blogRemark;
    @TableField(value = "blog_comment")
    private Integer blogComment;
    @TableField(value = "blog_source")
    private String blogSource;
    @TableField(value = "gmt_create")
    private String blogCreateTime;
    @TableField(value = "gmt_modified")
    private String blogModifiedTime;
    @TableField(value = "blog_version")
    private Integer blogVersion;
    @TableField(value = "is_deleted")
    private Integer isDeleted;
}
