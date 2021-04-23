package com.gjw.gjwblog.controller;


import com.gjw.gjwblog.result.R;
import com.gjw.gjwblog.service.TypeService;
import com.gjw.gjwblog.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/type")
public class TypeController {

    @Autowired
    private TypeService typeService;

    @GetMapping("list")
    public R getAllTypeReturns() {
        List<TypeReturn> allTypesAndReturn = typeService.getAllTypesAndReturn();
        if (allTypesAndReturn != null) {
            return R.ok().data("typeReturns", allTypesAndReturn);
        }
        return R.error();
    }

    @GetMapping("listAllTypesReturn")
    public R listAllTypesReturn() {
        List<TypeListReturn> typeListReturns = typeService.listAllTypesReturn();
        if (typeListReturns != null) {
            return R.ok().data("typeListReturns", typeListReturns);
        }
        return R.error();
    }

    @PostMapping("getTypeByCondition")
    public R getTypeByCondition(@RequestBody TypeListParam typeListParam) {
        List<TypeListReturn> typeList = typeService.findTypesByTypeListParam(typeListParam);
        return R.ok().data("typeListReturns", typeList);
    }

    @PostMapping("insertOneType")
    public R saveOneType(@RequestBody TypeSaveParam typeSaveParam) {

        boolean flag = typeService.saveOneType(typeSaveParam);
        if (flag == true) {
            return R.ok().data("message", "插入分类成功");
        } else {
            return R.error();
        }

    }

    @GetMapping("getTypeById")
    public R getTypeById(@RequestParam("typeId") Integer typeId) {

        TypeReturn type = typeService.getTypeById(typeId);
        if (type != null) {
            return R.ok().data("typeEditReturn", type);
        } else {
            return R.error();
        }
    }

    @PostMapping("editOneType")
    public R editOneType(@RequestBody TypeEditParam typeEditParam) {

        boolean flag = typeService.editOneType(typeEditParam);
        if (flag == true) {
            return R.ok().data("message", "编辑成功");
        } else {
            return R.error();
        }
    }

    @GetMapping("deleteOneType")
    public R deleteOneType(@RequestParam("typeId") Integer typeId) {

        boolean flag = typeService.deleteOneType(typeId);
        if (flag == true) {

            return R.ok().data("message", "删除分类成功");
        } else {
            return R.error();
        }
    }


    @GetMapping("returnAllTypes")
    public R returnAllType() {

        boolean flag = typeService.returnAllType();
        if (flag) {
            return R.ok().data("message", "还原所有分类成功");
        } else {
            return R.error();
        }

    }

    @GetMapping("deleteAllTypes")
    public R deleteAllType() {

        boolean flag = typeService.deleteAllType();
        if (flag) {
            return R.ok().data("message", "删除所有分类成功");
        } else {
            return R.error();
        }
    }
}
