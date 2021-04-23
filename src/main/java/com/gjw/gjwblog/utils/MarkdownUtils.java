package com.gjw.gjwblog.utils;

import org.pegdown.PegDownProcessor;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

import java.io.FileReader;
import java.io.IOException;

public class MarkdownUtils {


    public static String mdToHtml(String path, @Nullable String imgAddr) throws IOException {

        StringBuilder sb = new StringBuilder();
        FileReader r = new FileReader(path);
        char[] cbuf = new char[1024];
        while (r.read(cbuf) != -1) {
            sb.append(cbuf);
        }
        PegDownProcessor pdp = new PegDownProcessor(Integer.MAX_VALUE);
        String html = String.valueOf(sb);
        html = pdp.markdownToHtml(html);
        if (!StringUtils.isEmpty(imgAddr)) {
            //将html中路径"assets/***" 变为 "http://localhost:8082/note/assets/"
            String newHtml = StringUtils.replace(html, "/note/assets/", imgAddr);
            return newHtml;
        }
        return html;
    }

}
