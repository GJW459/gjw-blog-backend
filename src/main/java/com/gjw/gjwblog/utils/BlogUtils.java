package com.gjw.gjwblog.utils;

import com.gjw.gjwblog.vo.BlogParam;

/**
 * Blog工具包
 */
public class BlogUtils {


    public static BlogParam preModifyBlogParam(BlogParam blogParam) {
        String begin = blogParam.getBegin();
        String end = blogParam.getEnd();
        if (begin.equals("") && !end.equals("")) {
            blogParam.setBegin("0000-00-00 00:00:00");// begin为空 end不为空
        }
        if (end.equals("") && !begin.equals("")) {
            blogParam.setEnd(DateUtils.now());
        }
        return blogParam;
    }
}
