package com.gjw.gjwblog.controller;


import com.github.pagehelper.PageInfo;
import com.gjw.gjwblog.result.R;
import com.gjw.gjwblog.service.AboutService;
import com.gjw.gjwblog.utils.AboutUtils;
import com.gjw.gjwblog.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/about")
public class AboutController {


    @Autowired
    private AboutService aboutService;

    @PostMapping("saveOneAbout")
    public R saveOneAbout(@RequestBody AboutSaveParam aboutSaveParam) {

        boolean flag = aboutService.saveOneAbout(aboutSaveParam);
        if (flag == true) {
            return R.ok().data("message", "保存成功");
        } else {
            return R.error();
        }
    }

    @PostMapping("list")
    public R list(@RequestBody AboutParam aboutParam) {


        AboutParam preModifyAboutParam = AboutUtils.preModifyAboutParam(aboutParam);
        String begin = preModifyAboutParam.getBegin();
        String end = preModifyAboutParam.getEnd();
        int flag = end.compareTo(begin);// flag=-1 end<begin
        if (flag == -1) {
            // 格式错误 end不能大于begin
            return R.error();
        }
        PageInfo<AboutListReturn> aboutListReturnPageInfo = aboutService.listAboutByCondition(preModifyAboutParam);
        if (aboutListReturnPageInfo != null) {
            return R.ok().data("abouts", aboutListReturnPageInfo);
        }
        return R.error();
    }

    @PostMapping("editOneAbout")
    public R editOneAbout(@RequestBody AboutEditParam aboutEditParam) {

        boolean flag = aboutService.editOneAbout(aboutEditParam);
        if (flag == true) {
            return R.ok().data("message", "编辑成功");
        } else {
            return R.error();
        }
    }

    @GetMapping("getAboutById")
    public R getEditAboutInfo(@RequestParam("aboutId") Integer aboutId) {
        try {
            AboutEditReturn aboutEditReturn = aboutService.getEditAboutInfo(aboutId);
            return R.ok().data("aboutEditReturn", aboutEditReturn);
        } catch (IOException e) {
            e.printStackTrace();
            return R.error();
        }
    }

    @GetMapping("getAboutHtml")
    public R getAboutHtml(@RequestParam("aboutId") Integer aboutId) {

        try {
            AboutHtmlReturn aboutHtmlReturn = aboutService.getAboutHtml(aboutId);
            return R.ok().data("aboutHtmlReturn", aboutHtmlReturn);
        } catch (IOException e) {
            e.printStackTrace();
            return R.error();
        }
    }

    @GetMapping("deleteOneAbout")
    public R deleteOneAbout(@RequestParam("aboutId") Integer aboutId) {
        boolean flag = aboutService.deleteOneAbout(aboutId);
        if (flag == true) {
            return R.ok().data("message", "删除about成功");
        } else {
            return R.error();
        }
    }

    @GetMapping("clearAllAbout")
    public R clearAllAbout() {
        boolean flag = aboutService.deleteAllAbout();
        if (flag == true) {
            return R.ok().data("message", "删除所有about成功");
        } else {
            return R.error();
        }
    }

    @GetMapping("returnAllAbout")
    public R returnAllAbout() {
        boolean flag = aboutService.returnAllAbout();
        if (flag == true) {
            return R.ok().data("message", "还原所有about成功");
        } else {
            return R.error();
        }
    }

}
