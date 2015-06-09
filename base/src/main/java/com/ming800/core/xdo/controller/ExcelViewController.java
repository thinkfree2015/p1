package com.ming800.core.xdo.controller;


import com.ming800.core.does.model.Do;
import com.ming800.core.does.model.DoResult;
import com.ming800.core.does.model.Page;
import com.ming800.core.does.model.PageField;
import com.ming800.core.p.PConst;
import com.ming800.core.p.model.DictionaryData;
import com.ming800.core.p.model.MethodCache;
import com.ming800.core.p.model.SystemLog;
import com.ming800.core.p.service.ModuleManager;
import com.ming800.core.base.service.BaseManager;
import com.ming800.core.util.ApplicationContextUtil;
import com.ming800.core.util.AuthorizationUtil;
import com.ming800.core.util.DateUtil;
import com.ming800.core.util.SystemValueUtil;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-1-9
 * Time: 上午11:40
 * To change this template use File | Settings | File Templates.
 */
public class ExcelViewController extends AbstractExcelView {

    private BaseManager baseManager = (BaseManager) ApplicationContextUtil.getApplicationContext().getBean("baseManagerImpl");
    private ModuleManager moduleManager = (ModuleManager) ApplicationContextUtil.getApplicationContext().getBean("moduleManagerImpl");

    @SuppressWarnings("unchecked")
    protected void buildExcelDocument(Map map, HSSFWorkbook hssfWorkbook, HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<Object> objectList = (List<Object>) map.get("objectList");
        Do tempDo = (Do) map.get("tempDo");
        Page tempPage = (Page) map.get("tempPage");

        /*缓存*/
        MethodCache methodCache = new MethodCache();
        methodCache.init(tempDo, tempPage);

        HSSFSheet hssfSheet = hssfWorkbook.createSheet();
        int fi = 0;
        for (PageField pageField : tempPage.getFieldList()) {
            if (!pageField.getInputType().equals("default")) {
                this.getCell(hssfSheet, 0, fi).setCellValue(new HSSFRichTextString(pageField.getLabel()));
                fi++;
            }
        }

        for (Object object : objectList) {
            int rowCount = hssfSheet.getLastRowNum();
            int fd = 0;
            for (PageField pageField : tempPage.getFieldList()) {
                if (!pageField.getInputType().equals("default")) {
                    Object tempObjectValue = SystemValueUtil.fetchFieldValue(object, methodCache, pageField.getName().split("\\."));
//                  Object tempObjectValue = SystemValueUtil.generateTempObjectValue(object, pageField.getName().split("\\."));
                    if (tempObjectValue == null) {
                        tempObjectValue = "";
                    } else {
                        if (pageField.getDataType().equals("status")) {
                            tempObjectValue = moduleManager.convertStatusTypeLabel(pageField.getKey(), tempObjectValue.toString());
                        } else if (pageField.getInputType().equals("select_dictionary") || pageField.getInputType().equals("radio_dictionary")) {
                            DictionaryData dictionaryData = (DictionaryData) baseManager.getObject(DictionaryData.class.getName(), tempObjectValue.toString());
                            tempObjectValue = dictionaryData.getData();
                        } else if (pageField.getDataType().equals("datetime")) {
                            tempObjectValue = DateUtil.formatDateMinute((Date) tempObjectValue);
                        } else if (pageField.getDataType().equals("date")) {
                            tempObjectValue = DateUtil.formatDate((Date) tempObjectValue);
                        }
                    }

                    this.getCell(hssfSheet, rowCount + 1, fd).setCellValue(new HSSFRichTextString(tempObjectValue.toString()));
                    fd++;
                }
            }
        }

        DoResult doResult = (DoResult) map.get("doResult");
        SystemLog systemLog = new SystemLog();

        systemLog.setContent(doResult.getLabel() + "导出成功");
        systemLog.setTheDateTime(new Date());
        //systemLog.setCityShotType(AuthorizationUtil.getMyCity());

        systemLog.setUser(AuthorizationUtil.getUser());
        systemLog.setTheType(PConst.SYSTEM_LOG_THE_TYPE_EXPORT);
        baseManager.saveOrUpdate(systemLog.getClass().getName(), systemLog);


        //1.设置文件ContentType类型，这样设置，会自动判断下载文件类型
        response.setContentType("multipart/form-data");
        //2.设置文件头：最后一个参数是设置下载文件名(假如我们叫a.pdf)
        response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(tempDo.getLabel(), "UTF-8") + ".xls");
    }
}

