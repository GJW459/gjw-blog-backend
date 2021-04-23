package com.gjw.gjwblog.dao;

import com.gjw.gjwblog.entity.Type;
import com.gjw.gjwblog.vo.TypeEditParam;
import com.gjw.gjwblog.vo.TypeListParam;
import com.gjw.gjwblog.vo.TypeListReturn;
import com.gjw.gjwblog.vo.TypeReturn;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * #{ }是预编译处理，MyBatis在处理#{ }时，它会将sql中的#{ }替换为？，
 * 然后调用PreparedStatement的set方法来赋值，传入字符串后，会在值两边加上单引号，使用占位符的方式提高效率，可以防止sql注入。
 * ${}:表示拼接sql串，将接收到参数的内容不加任何修饰拼接在sql中，可能引发sql注入。
 */
public interface TypeMapper {

    List<TypeReturn> getAllTypesAndReturn();

    void insertOneType(@Param("type") Type type);

    List<TypeListReturn> getAllTypeListReturn();

    List<TypeListReturn> getTypesByTypeListParam(@Param("typeListParam") TypeListParam typeListParam);

    int countAllType();

    TypeReturn getOneTypeByTypeName(@Param("typeName") String typeName);

    TypeReturn getOneTypeById(@Param("typeId") Integer typeId);

    void updateOneType(@Param("typeEditParam") TypeEditParam typeEditParam);

    void deleteOneType(@Param("typeId") Integer typeId);

    /**
     * 实现博客数自增
     */
    void increaseTypeBlogCount(@Param("typeId") Integer typeId);

    /**
     * 实现博客数自减
     */
    void decreaseTypeBlogCount(@Param("typeId") Integer typeId);

    /**
     * 清空bl_type表的type_blog_count
     */
    void clearTypeBlogCount();

    void returnAllType();

    Integer getTypeCount();

    void deleteAllType();

}
