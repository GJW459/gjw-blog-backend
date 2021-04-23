package com.gjw.gjwblog.vo;


import lombok.Data;

@Data
public class BlogParam {

    private String title;

    private Integer type;

    private String begin;

    private String end;

    private Integer currentPage;

    private Integer pageSize;
}
