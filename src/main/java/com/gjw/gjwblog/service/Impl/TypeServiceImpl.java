package com.gjw.gjwblog.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gjw.gjwblog.dao.TypeMapper;
import com.gjw.gjwblog.entity.Type;
import com.gjw.gjwblog.service.TypeService;
import com.gjw.gjwblog.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service("typeService")
public class TypeServiceImpl implements TypeService {
    @Autowired
    private TypeMapper typeMapper;

    private Lock lock = new ReentrantLock();

    @Override
    public List<TypeReturn> getAllTypesAndReturn() {
        List<TypeReturn> typesAndReturn = typeMapper.getAllTypesAndReturn();
        return typesAndReturn;
    }

    /**
     * save 一个 type
     *
     * @param typeSaveParam
     * @return
     */
    @Transactional
    @Override
    public boolean saveOneType(TypeSaveParam typeSaveParam) {

        Type type = new Type();
        // 初始化
        type.setIsDeleted(0);
        type.setIsEnable(1);
        type.setTypeBlogCount(0);
        type.setTypeName(typeSaveParam.getTypeName());
        // 先查询是否有此类别
        TypeReturn oneTypeByTyName = typeMapper.getOneTypeByTypeName(typeSaveParam.getTypeName());
        if (oneTypeByTyName == null) {
            //没有当前类别可以插入
            int startCount, endCount;
            startCount = typeMapper.countAllType();
            lock.lock();
            typeMapper.insertOneType(type);
            lock.unlock();
            endCount = typeMapper.countAllType();
            if ((endCount - startCount) == 1) {
                //插入成功
                return true;
            } else {
                return false;
            }

        } else {
            //有当前类别了 不可以插入
            return false;
        }
    }

    @Override
    public List<TypeListReturn> listAllTypesReturn() {
        return typeMapper.getAllTypeListReturn();
    }

    @Override
    public List<TypeListReturn> findTypesByTypeListParam(TypeListParam typeListParam) {
        return typeMapper.getTypesByTypeListParam(typeListParam);
    }

    @Override
    public TypeReturn getTypeById(Integer typeId) {
        return typeMapper.getOneTypeById(typeId);
    }

    @Transactional
    @Override
    public boolean editOneType(TypeEditParam typeEditParam) {
        lock.lock();
        typeMapper.updateOneType(typeEditParam);
        lock.unlock();
        //判断是否修改
        TypeReturn type = typeMapper.getOneTypeByTypeName(typeEditParam.getTypeName());
        if (type != null) {
            //修改成功
            return true;
        } else {
            return false;
        }
    }

    @Transactional
    @Override
    public boolean deleteOneType(Integer typeId) {

        lock.lock();
        // 删除当前分类 对应的博客就不显示了
        typeMapper.deleteOneType(typeId);
        lock.unlock();
        // 是否删除成功
        TypeReturn typeReturn = typeMapper.getOneTypeById(typeId);
        if (typeReturn == null) {
            // 删除成功
            return true;
        } else {
            return false;
        }
    }

    /**
     * 还原所有分类
     *
     * @return
     */
    @Override
    public boolean returnAllType() {
        lock.lock();
        typeMapper.returnAllType();
        lock.unlock();
        Integer typeCount = typeMapper.getTypeCount();
        if (typeCount != 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean deleteAllType() {


        lock.lock();
        typeMapper.deleteAllType();
        lock.unlock();
        Integer typeCount = typeMapper.getTypeCount();
        if (typeCount == 0) {
            return true;
        } else {
            return false;
        }
    }

}
