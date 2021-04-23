package com.gjw.gjwblog.entity;

import lombok.Data;

@Data
public class About {

    private Integer aboutId;
    private String aboutTitle;
    private String aboutPath;
    private Integer aboutRead;
    private String aboutCreateTime;
    private String aboutModifiedTime;
    private Integer aboutVersion;
    private Integer isDeleted;
}
