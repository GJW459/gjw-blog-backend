package com.gjw.gjwblog.vo;


import lombok.Data;

/**
 * @author gjw
 * about表查询参数
 */
@Data
public class AboutParam {

    private String title;
    private String begin;
    private String end;
    private Integer currentPage;
    private Integer pageSize;
}
