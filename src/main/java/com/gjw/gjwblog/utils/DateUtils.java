package com.gjw.gjwblog.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    /**
     * @return 返回yyyy-MM-dd HH:mm:ss
     */
    public static String now() {

        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        return sf.format(new Date());//返回当前时间
    }

    /**
     * @return 返回yyyyMMddHHmmss
     */
    public static String now2() {

        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sf.format(new Date());// 返回当前时间
    }
}
