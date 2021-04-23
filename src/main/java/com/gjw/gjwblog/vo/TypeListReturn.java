package com.gjw.gjwblog.vo;

import lombok.Data;

@Data
public class TypeListReturn {

    private Integer typeId;
    private String typeName;
    private Integer typeBlogCount;
    private Integer isEnable;
}
