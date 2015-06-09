package com.ming800.core.base.dao;

import com.ming800.core.taglib.PageEntity;
import com.ming800.core.util.PageInfo;
import org.hibernate.Query;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: ming
 * Date: 12-10-15
 * Time: 下午2:01
 * To change this template use File | Settings | File Templates.
 */
public interface BaseDao<T> {

    public T getObject(Integer id);

    public T getObject(String id);

    public void saveObject(T entity);

    public void updateObject(T entity);

    public void saveOrUpdateObject(T entity);

    public void deleteObject(T entity);

    public List<T> getObjectList(String hql, Object... params);

    public T getUniqueObjectByConditions(String queryHql, LinkedHashMap<String, Object> queryParamMap);

    public List<T> getObjectListByConditions(String queryHql, LinkedHashMap<String, Object> queryParamMap);

    public PageInfo getPageByConditions(PageEntity pageEntity, String queryString, Object... params);

    public PageInfo getPageByConditions(PageEntity pageEntity, String queryHql, LinkedHashMap<String, Object> queryParamMap);

    public PageInfo getPage(Query listQuery, Query countQuery, PageEntity pageEntity);
}
