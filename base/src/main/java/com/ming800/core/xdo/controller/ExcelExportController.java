package com.ming800.core.xdo.controller;


import com.ming800.core.base.service.BaseManager;
import com.ming800.core.does.model.Do;
import com.ming800.core.does.model.DoResult;
import com.ming800.core.does.model.Page;
import com.ming800.core.does.model.PageField;
import com.ming800.core.p.PConst;
import com.ming800.core.p.model.DictionaryData;
import com.ming800.core.p.model.MethodCache;
import com.ming800.core.p.model.MethodSetting;
import com.ming800.core.p.model.SystemLog;
import com.ming800.core.p.service.ModuleManager;
import com.ming800.core.util.AuthorizationUtil;
import com.ming800.core.util.DateUtil;
import com.ming800.core.util.SystemValueUtil;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-1-9
 * Time: 上午11:40
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/excelExportController")
public class ExcelExportController extends BaseController {

    @Autowired
    private BaseManager baseManager;
    @Autowired
    private ModuleManager moduleManager;


    @RequestMapping("/buildExcelDocument.do")
    public void buildExcelDocument(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelMap map = (ModelMap) request.getAttribute("modelMap");
        List<Object> objectList = (List<Object>) map.get("objectList");
        Do tempDo = (Do) map.get("tempDo");
        Page tempPage = (Page) map.get("tempPage");

        /*缓存*/
        MethodCache methodCache = new MethodCache();
        methodCache.init(tempDo, tempPage);

//        HSSFSheet hssfSheet = hssfWorkbook.createSheet();
        SXSSFWorkbook wb = new SXSSFWorkbook(500); // keep 500 rows in memory, exceeding rows will be flushed to disk
        Sheet sh = wb.createSheet();
        int fi = 0;
        Row headerRow = sh.createRow(0);
        for (PageField pageField : tempPage.getFieldList()) {
            if (!pageField.getInputType().equals("default")) {
                headerRow.createCell(fi).setCellValue(new HSSFRichTextString(pageField.getLabel()));
                fi++;
            }
        }

        for (Object object : objectList) {
            int rowCount = sh.getLastRowNum();
            Row row = sh.createRow(rowCount + 1);
            int fd = 0;
            for (PageField pageField : tempPage.getFieldList()) {
                if (!pageField.getInputType().equals("default")) {
                    Object tempObjectValue = SystemValueUtil.fetchFieldValue(object, methodCache, pageField.getName().split("\\."));
//                  Object tempObjectValue = SystemValueUtil.generateTempObjectValue(object, pageField.getName().split("\\."));

                    Cell tempCell = row.createCell(fd);
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
                        } else {
                            MethodSetting methodSetting = methodCache.getMethodSetting(object.getClass(), pageField.getName().split("\\.")[0]);
                            Method method = methodSetting.getMethod();
                            String simpleName = method.getReturnType().getSimpleName();
                            if (simpleName.equals("Integer") || simpleName.equals("BigDecimal") || simpleName.equals("float")) {
                                tempCell.setCellType(Cell.CELL_TYPE_NUMERIC);
                            }
                        }
                    }
                    if (tempCell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                        tempCell.setCellValue(Double.parseDouble(tempObjectValue.toString()));
                    } else {
                        tempCell.setCellValue(new HSSFRichTextString(tempObjectValue.toString()));
                    }

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
        response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(tempDo.getLabel(), "UTF-8") + ".xlsx");
        wb.write(response.getOutputStream());
    }

}

