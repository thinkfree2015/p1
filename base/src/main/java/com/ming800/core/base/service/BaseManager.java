package com.ming800.core.base.service;

import com.ming800.core.does.model.Field;
import com.ming800.core.p.model.StatusTypeItem;
import com.ming800.core.taglib.PageEntity;
import com.ming800.core.util.PageInfo;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: ming
 * Date: 12-11-10
 * Time: 上午11:27
 * To change this template use File | Settings | File Templates.
 */
public interface BaseManager {


    /**
     * 获取对象
     *
     * @param model 类的完整路径（含包路径）  例如 com.ming800.xedu.student.BigStudent
     *              获取方式完整路径的方式：1、BigStudent.class.getName();    2、bigStudent.getClass().getName
     * @param id
     * @return
     */
    public Object getObject(String model, String id);

    /**
     * @param model  类的完整路径（含包路径）
     * @param object 要保存的对象
     */
    public void saveOrUpdate(String model, Object object);

    /**
     * @param model 类的完整路径（含包路径）
     * @param id
     */
    public void delete(String model, String id);

    /**
     * 假删
     * @param model
     * @param id
     */
    public void remove(String model, String id);
    /**
     * 列表查询 （需要在controller中写查询语句和查询参数）
     *
     * @param queryHql      查询语句 （hql格式）
     * @param queryParamMap 查询参数
     * @return
     */
    public List listObject(String queryHql, LinkedHashMap<String, Object> queryParamMap);

    public List listObject(String queryHql, Object... params);

    public PageInfo listPageInfo(String queryHql, PageEntity pageEntity, LinkedHashMap<String, Object> queryParamMap);

    public Object getUniqueObjectByConditions(String queryHql, LinkedHashMap<String, Object> queryParamMap);

    /**
     * 批量删除  （假删  将对象的theStatus状态改为0）
     *
     * @param model   类的完整路径（含包路径）
     * @param idArray 要删除的id数组
     * @return
     */
    public int batchRemove(String model, String[] idArray);

    /**
     * 批量删除  （真删 ）
     *
     * @param model   类的完整路径（含包路径）
     * @param idArray 要删除的id数组
     * @return
     */
    public int batchDelete(String model, String[] idArray);


    /**
     * 批量保存  （保存  ）
     *
     * @param modelType  类的完整路径（含包路径）
     * @param objectList 要保存的对象list
     * @return
     */
    public void batchSaveOrUpdate(String type, String modelType, List<Object> objectList);

    public Object executeSql(String type, String queryStr, LinkedHashMap<String, Object> queryParamMap);

    public Boolean executeBranchData(String name);


    /*
        通过配置module文件来配置固定类型，以下方法是用来获取固定类型的列表
     */
    public Map<Field, List<StatusTypeItem>> listStatusType(String entityName, List<String> fieldNameList);

    public Map<Field, List<StatusTypeItem>> listStatusType(String entityName, String[] fieldNameList);

    public List<StatusTypeItem> listStatusType(String entityName, String fieldName);

}
