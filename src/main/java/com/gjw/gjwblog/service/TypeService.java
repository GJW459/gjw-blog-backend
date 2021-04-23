package com.gjw.gjwblog.service;

import com.gjw.gjwblog.vo.*;

import java.util.List;

public interface TypeService {

    List<TypeReturn> getAllTypesAndReturn();

    boolean saveOneType(TypeSaveParam typeSaveParam);

    List<TypeListReturn> listAllTypesReturn();

    List<TypeListReturn> findTypesByTypeListParam(TypeListParam typeListParam);

    TypeReturn getTypeById(Integer typeId);

    boolean editOneType(TypeEditParam typeEditParam);

    boolean deleteOneType(Integer typeId);

    boolean returnAllType();

    boolean deleteAllType();
}
