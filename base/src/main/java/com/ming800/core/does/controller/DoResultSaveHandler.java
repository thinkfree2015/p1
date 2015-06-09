package com.ming800.core.does.controller;

import com.ming800.core.base.service.BaseManager;
import com.ming800.core.xdo.service.XdoManager;
import com.ming800.core.does.model.Do;
import com.ming800.core.does.model.DoResult;
import com.ming800.core.does.service.DoHandler;
import com.ming800.core.util.ApplicationContextUtil;
import org.springframework.ui.ModelMap;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by IntelliJ IDEA.
 * User: ming
 * Date: 12-10-30
 * Time: 上午10:30
 * To change this template use File | Settings | File Templates.
 */

public class DoResultSaveHandler implements DoHandler {

    private BaseManager baseManager = (BaseManager) ApplicationContextUtil.getApplicationContext().getBean("baseManagerImpl");
    private XdoManager xdoManager = (XdoManager) ApplicationContextUtil.getApplicationContext().getBean("xdoManagerImpl");

    @Override
    public ModelMap handle(ModelMap modelMap, HttpServletRequest request) throws Exception {

        DoResult doResult = new DoResult();
        String type = "new";
        String id = request.getParameter("id");
        if (id != null && !id.equals("")) {
            type = "edit";
            doResult = (DoResult) baseManager.getObject(DoResult.class.getName(), id);
        }

        Do tempDo = (Do) modelMap.get("tempDo");
        doResult = (DoResult) xdoManager.processSaveOrUpdateTempObject(tempDo, doResult, doResult.getClass(), request, type);

        String sortName = request.getParameter("sortName");
        String sortOrder = request.getParameter("sortOrder");

        if (sortName != null && !sortName.equals("")) {
            doResult.setConditions(doResult.getConditions() + ";" + sortOrder + ":" + sortName);
        }


        modelMap.put("object", doResult);

        return modelMap;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
