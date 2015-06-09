package com.ming800.core.p.service;

import com.ming800.core.does.model.Do;
import com.ming800.core.p.model.Module;
import com.ming800.core.p.model.StatusType;
import com.ming800.core.p.model.StatusTypeItem;
import com.ming800.core.p.model.Xentity;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-4-20
 * Time: 下午4:18
 * To change this template use File | Settings | File Templates.
 */
public interface ModuleManager {


    Map<String, Module> fetchModuleMap();

    Map<String, Xentity> fetchXentityMap();

    Module fetchModule(String moduleName);

    Xentity fetchXentity(String xentityName);

    String convertStatusTypeLabel(String key, String propertyValue);

    public StatusType fetchStatusType(String key);

    public List<StatusTypeItem> listStatusTypeItem(String key);

}
