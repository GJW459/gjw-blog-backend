package com.gjw.gjwblog.entity;


import lombok.Data;

@Data
public class Type {

    private Integer typeId;
    private String typeName;
    private Integer typeBlogCount;
    private Integer isEnable;
    private Integer isDeleted;

}
