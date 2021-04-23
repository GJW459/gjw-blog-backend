package com.gjw.gjwblog.vo;


import lombok.Data;

@Data
public class AboutListReturn {

    private Integer aboutId;
    private String aboutTitle;
    private Integer aboutRead;
    private String aboutCreateTime;
    private String aboutModifiedTime;
}
