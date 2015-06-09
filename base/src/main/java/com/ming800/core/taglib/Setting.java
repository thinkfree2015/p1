package com.ming800.core.taglib;

import com.ming800.core.p.model.Module;
import com.ming800.core.p.model.Xentity;
import com.ming800.core.p.service.ModuleManager;
import com.ming800.core.util.ApplicationContextUtil;
import com.ming800.core.util.AuthorizationUtil;
import com.ming800.organization.OrganizationConst;
import com.ming800.organization.model.ConfigProperty;
import com.ming800.organization.model.Permission;
import com.ming800.organization.model.Role;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-12-1
 * Time: 下午2:10
 * To change this template use File | Settings | File Templates.
 */

public class Setting extends TagSupport {
    private ModuleManager moduleManager = (ModuleManager) ApplicationContextUtil.getApplicationContext().getBean("moduleManagerImpl");
    private String name;
    private Map<String, Module> moduleMap;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int doStartTag() throws JspException {
        moduleMap = moduleManager.fetchModuleMap();
        if (processSetting()) {
            return 1;
        } else {
            return 0;
        }
    }

    public Map<String, Module> getModuleMap() {
        return moduleMap;
    }

    public void setModuleMap(Map<String, Module> moduleMap) {
        this.moduleMap = moduleMap;
    }

    public boolean processSetting() {


        if (name == null || name.equals("")) {
            return true;
        }
        Map<String, String> settingMap = AuthorizationUtil.getMyUser().getSettingMap();
        for (String s : name.split(";")) {
            String key = s.split(":")[0];
            String value = s.split(":")[1];


            if (value.startsWith("*")) {  //星号开头的值 配置了反而不显示
                if ((settingMap.containsKey(key) && settingMap.get(key).equals(value.substring(1, value.length()))) || (!settingMap.containsKey(key) && getDefaultValueByName(key).equals(value))) {
                    return false;
                } else {
                    return true;
                }
            } else {
                if ((settingMap.containsKey(key) && settingMap.get(key).equals(value)) || (!settingMap.containsKey(key) && getDefaultValueByName(key).equals(value))) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }


    private String getDefaultValueByName(String keyName) {

        Iterator iterator = moduleMap.keySet().iterator();
        while (iterator.hasNext()) {
            String key = (String) iterator.next();
            Module tempModule = moduleMap.get(key);

            for (ConfigProperty configProperty : tempModule.getConfigPropertyList()) {

                if (configProperty.getName().equals(keyName)) {
                    return configProperty.getDefaultValue();
                }


            }


        }

        return "";
    }

}
