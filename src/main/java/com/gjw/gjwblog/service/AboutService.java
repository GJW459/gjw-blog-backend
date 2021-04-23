package com.gjw.gjwblog.service;

import com.github.pagehelper.PageInfo;
import com.gjw.gjwblog.vo.*;

import java.io.IOException;

public interface AboutService {

    boolean saveOneAbout(AboutSaveParam aboutSaveParam);

    PageInfo<AboutListReturn> listAboutByCondition(AboutParam aboutParam);

    boolean editOneAbout(AboutEditParam aboutEditParam);

    AboutEditReturn getEditAboutInfo(Integer aboutId) throws IOException;

    AboutHtmlReturn getAboutHtml(Integer aboutId) throws IOException;

    boolean deleteOneAbout(Integer aboutId);

    boolean deleteAllAbout();

    boolean returnAllAbout();
}
