package com.gjw.gjwblog.utils;

import com.gjw.gjwblog.vo.AboutParam;

/**
 * 关于我的工具包
 */
public class AboutUtils {


    public static AboutParam preModifyAboutParam(AboutParam aboutParam) {
        String begin = aboutParam.getBegin();
        String end = aboutParam.getEnd();
        if (begin.equals("") && !end.equals("")) {
            // begin为空 end不为空
            aboutParam.setBegin("0000-00-00 00:00:00");
        }
        if (end.equals("") && !begin.equals("")) {
            //begin不为空 end为空时
            aboutParam.setEnd(DateUtils.now());
        }
        return aboutParam;
    }


}
