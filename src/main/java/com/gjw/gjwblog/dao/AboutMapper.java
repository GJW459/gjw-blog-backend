package com.gjw.gjwblog.dao;

import com.gjw.gjwblog.entity.About;
import com.gjw.gjwblog.vo.AboutListReturn;
import com.gjw.gjwblog.vo.AboutParam;
import com.gjw.gjwblog.vo.resultmap.AboutEditParamType;
import com.gjw.gjwblog.vo.resultmap.AboutEditResultMap;
import com.gjw.gjwblog.vo.resultmap.AboutHtmlResultMap;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AboutMapper {

    void insertOneAbout(@Param("about") About about);

    Integer countAboutByAboutTitle(@Param("aboutTitle") String aboutTitle);

    List<AboutListReturn> getAllAboutListReturnByCondition(@Param("aboutParam") AboutParam aboutParam);

    void editOneAbout(@Param("aboutEditParamType") AboutEditParamType aboutEditParamType);

    String getAboutTitleById(@Param("aboutId") Integer aboutId);

    Integer getAboutNumber(@Param("aboutTitle") String aboutTitle);

    AboutEditResultMap getAboutById(@Param("aboutId") Integer aboutId);

    AboutHtmlResultMap getAboutHtml(@Param("aboutId") Integer aboutId);

    void deleteOneAbout(@Param("aboutId") Integer aboutId);

    void deleteAllAbout();

    void returnAllAbout();

    Integer countAboutById(@Param("aboutId") Integer aboutId);

    Integer countAbout();

}
