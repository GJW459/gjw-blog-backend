package com.gjw.gjwblog.service.Impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gjw.gjwblog.dao.AboutMapper;
import com.gjw.gjwblog.entity.About;
import com.gjw.gjwblog.service.AboutService;
import com.gjw.gjwblog.utils.DateUtils;
import com.gjw.gjwblog.utils.FileUtils;
import com.gjw.gjwblog.utils.MarkdownUtils;
import com.gjw.gjwblog.vo.*;
import com.gjw.gjwblog.vo.resultmap.AboutEditParamType;
import com.gjw.gjwblog.vo.resultmap.AboutEditResultMap;
import com.gjw.gjwblog.vo.resultmap.AboutHtmlResultMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service("aboutService")
public class AboutServiceImpl implements AboutService {


    @Value("${file.upload.absolutePath}")
    private String absolutePath;

    private Lock lock = new ReentrantLock();

    @Autowired
    private AboutMapper aboutMapper;


    @Transactional
    @Override
    public boolean saveOneAbout(AboutSaveParam aboutSaveParam) {

        //保存的步骤 先对上传的参数进行验证 如果aboutTitle在数据库中已经存在就失败
        // aboutTitle不存在才继续进行保存

        Integer count = aboutMapper.countAboutByAboutTitle(aboutSaveParam.getAboutTitle());
        if (count == 0) {
            // 当count为0的时候才能进行保存
            About about = new About();
            about.setAboutTitle(aboutSaveParam.getAboutTitle());
            about.setAboutRead(0);
            about.setAboutVersion(1);
            about.setIsDeleted(0);
            about.setAboutCreateTime(DateUtils.now());
            about.setAboutModifiedTime(DateUtils.now());
            String aboutContent = aboutSaveParam.getAboutContent();
            String aboutPath = absolutePath + aboutSaveParam.getAboutTitle() + ".md";
            boolean flag = FileUtils.stringToFile(aboutContent, aboutPath);
            if (flag == true) {
                //开始保存
                about.setAboutPath(aboutPath);
                lock.lock();
                //about 插入到数据库
                aboutMapper.insertOneAbout(about);
                lock.unlock();
                return true;
            }
            return false;
        } else {
            return false;
        }
    }

    @Override
    public PageInfo<AboutListReturn> listAboutByCondition(AboutParam aboutParam) {
        PageHelper.startPage(aboutParam.getCurrentPage(), aboutParam.getPageSize());
        List<AboutListReturn> aboutListReturns = aboutMapper.getAllAboutListReturnByCondition(aboutParam);
        PageInfo<AboutListReturn> pageInfo = new PageInfo<>(aboutListReturns);
        return pageInfo;
    }

    private boolean ableEditOneAbout(AboutEditParam aboutEditParam) {
        AboutEditParamType aboutEditParamType = new AboutEditParamType();
        aboutEditParamType.setAboutTitle(aboutEditParam.getAboutTitle());
        aboutEditParamType.setAboutId(aboutEditParam.getAboutId());
        aboutEditParamType.setAboutModifiedTime(DateUtils.now());
        String aboutContent = aboutEditParam.getAboutContent();
        String aboutPath = absolutePath + aboutEditParam.getAboutTitle() + ".md";
        boolean flag = FileUtils.stringToFile(aboutContent, aboutPath);
        if (flag == true) {
            //开始edit
            aboutEditParamType.setAboutPath(aboutPath);
            lock.lock();
            aboutMapper.editOneAbout(aboutEditParamType);
            lock.unlock();
            return true;
        } else {
            return false;
        }
    }

    @Transactional
    @Override
    public boolean editOneAbout(AboutEditParam aboutEditParam) {
        // 先查询是否标题改变了
        String oldAboutTitle = aboutMapper.getAboutTitleById(aboutEditParam.getAboutId());
        if (!oldAboutTitle.equals(aboutEditParam.getAboutTitle())) {

            //不相同时 先判断是否这个标题在数据库里面是否已经存在了
            Integer aboutNumber = aboutMapper.getAboutNumber(aboutEditParam.getAboutTitle());
            if (aboutNumber == 0) {
                //可以进行编辑
                return this.ableEditOneAbout(aboutEditParam);
            } else {
                // 不可以进行编辑
                return false;
            }
        } else {

            // 如果about两个标题相同就代表没有进行标题修改，直接进行edit
            return this.ableEditOneAbout(aboutEditParam);
        }
    }

    @Override
    public AboutEditReturn getEditAboutInfo(Integer aboutId) throws IOException {
        AboutEditResultMap about = aboutMapper.getAboutById(aboutId);
        AboutEditReturn aboutEditReturn = new AboutEditReturn();
        aboutEditReturn.setAboutId(about.getAboutId());
        aboutEditReturn.setAboutTitle(about.getAboutTitle());
        String aboutContent = FileUtils.fileToString(about.getAboutPath());
        aboutEditReturn.setAboutContent(aboutContent);
        return aboutEditReturn;
    }

    @Override
    public AboutHtmlReturn getAboutHtml(Integer aboutId) throws IOException {

        AboutHtmlResultMap about = aboutMapper.getAboutHtml(aboutId);
        AboutHtmlReturn aboutHtmlReturn = new AboutHtmlReturn();
        aboutHtmlReturn.setAboutTitle(about.getAboutTitle());
        // 将md文件装换成html 字符串
        String aboutHtml = MarkdownUtils.mdToHtml(about.getAboutPath(), null);
        aboutHtmlReturn.setAboutHtml(aboutHtml);
        return aboutHtmlReturn;
    }

    @Transactional
    @Override
    public boolean deleteOneAbout(Integer aboutId) {
        lock.lock();
        aboutMapper.deleteOneAbout(aboutId);
        lock.unlock();
        Integer count = aboutMapper.countAboutById(aboutId);
        if (count == 0) {
            //删除成功
            return true;
        } else {
            return false;
        }
    }

    @Transactional
    @Override
    public boolean deleteAllAbout() {
        lock.lock();
        aboutMapper.deleteAllAbout();
        lock.unlock();
        Integer count = aboutMapper.countAbout();
        if (count == 0) {
            //删除成功
            return true;
        } else {
            return false;
        }
    }

    @Transactional
    @Override
    public boolean returnAllAbout() {
        lock.lock();
        aboutMapper.returnAllAbout();
        lock.unlock();
        Integer count = aboutMapper.countAbout();
        if (count == 0) {
            //还原失败
            return false;
        } else {
            return true;
        }
    }
}
