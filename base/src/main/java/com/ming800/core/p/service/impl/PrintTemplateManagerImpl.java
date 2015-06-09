package com.ming800.core.p.service.impl;

import com.ming800.core.p.dao.PrintTemplateDao;
import com.ming800.core.p.model.PrintTemplate;
import com.ming800.core.p.service.PrintTemplateManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: ming
 * Date: 2010-1-28
 * Time: 19:10:47
 * To change this template use File | Settings | File Templates.
 */
@Service
public class PrintTemplateManagerImpl implements PrintTemplateManager {
    @Autowired
    private PrintTemplateDao printTemplateDao;


    @Override
    public PrintTemplate getPrintTemplate(String printTemplateId) {
        return this.printTemplateDao.getObject(printTemplateId);
    }

    @Override
    public List<PrintTemplate> getPrintTemplateList(String branchId, String[] theTypeArray) {
        String queryStr = "from PrintTemplate pt where pt.branch.id = :branchId";
        LinkedHashMap<String, Object> queryParamMap = new LinkedHashMap<>();
        queryParamMap.put("branchId", branchId);
        if (theTypeArray != null) {
            queryStr += " and pt.theType in (:theTypeArray)";
            queryParamMap.put("theTypeArray", theTypeArray);
        }

        queryStr += " order by pt.id desc";

        return this.printTemplateDao.getObjectListByConditions(queryStr, queryParamMap);  //To change body of implemented methods use File | Settings | File Templates.
    }


    @Override
    public void saveOrUpdatePrintTemplate(PrintTemplate printTemplate) {
        this.printTemplateDao.saveOrUpdateObject(printTemplate);//To change body of implemented methods use File | Settings | File Templates.
    }


   /* public void initPrintTemplate(Integer branchId){
        if(this.getPrintTemplateListByType(branchId,InfoConst.PRINT_TEMPLATE_CHARGE_ORDER_TYPE).size()==0) {
            String content = this.readFile(InfoConst.PRINT_TEMPLATE_CHARGE_ORDER_PATH);
            PrintTemplate printTemplate = new PrintTemplate();
            printTemplate.setBranchId(branchId);
            printTemplate.setName("收费单");
            printTemplate.setContent(content);
            printTemplate.setTheType(InfoConst.PRINT_TEMPLATE_CHARGE_ORDER_TYPE);
            this.saveOrUpdatePrintTemplate(printTemplate);
            content = this.readFile(InfoConst.PRINT_TEMPLATE_CHARGE_ORDER_TD_PATH);
            printTemplate = new PrintTemplate();
            printTemplate.setBranchId(branchId);
            printTemplate.setName("收费单(套打)");
            printTemplate.setContent(content);
            printTemplate.setTheType(InfoConst.PRINT_TEMPLATE_CHARGE_ORDER_TYPE);
            this.saveOrUpdatePrintTemplate(printTemplate);
        }

        if(this.getPrintTemplateListByType(branchId,InfoConst.PRINT_TEMPLATE_STUDENT_PAPER_TYPE).size()==0) {
            String content = this.readFile(InfoConst.PRINT_TEMPLATE_STUDENT_PAPER_PATH);
            PrintTemplate printTemplate = new PrintTemplate();
            printTemplate.setBranchId(branchId);
            printTemplate.setName("听课证");
            printTemplate.setContent(content);
            printTemplate.setTheType(InfoConst.PRINT_TEMPLATE_STUDENT_PAPER_TYPE);
            this.saveOrUpdatePrintTemplate(printTemplate);
            content = this.readFile(InfoConst.PRINT_TEMPLATE_STUDENT_PAPER_TD_PATH);
            printTemplate = new PrintTemplate();
            printTemplate.setBranchId(branchId);
            printTemplate.setName("听课证(套打)");
            printTemplate.setContent(content);
            printTemplate.setTheType(InfoConst.PRINT_TEMPLATE_STUDENT_PAPER_TYPE);
            this.saveOrUpdatePrintTemplate(printTemplate);
        }

        if(this.getPrintTemplateListByType(branchId,InfoConst.PRINT_TEMPLATE_STUDENT_PAPER_OEDER_TYPE).size()==0) {
            String content = this.readFile(InfoConst.PRINT_TEMPLATE_STUDENT_PAPER_ORDER_PATH);
            PrintTemplate printTemplate = new PrintTemplate();
            printTemplate.setBranchId(branchId);
            printTemplate.setName("听课证");
            printTemplate.setContent(content);
            printTemplate.setTheType(InfoConst.PRINT_TEMPLATE_STUDENT_PAPER_OEDER_TYPE);
            this.saveOrUpdatePrintTemplate(printTemplate);
        }

        if(this.getPrintTemplateListByType(branchId,InfoConst.PRINT_TEMPLATE_SALES_ORDER_TYPE).size()==0) {
            String content = this.readFile(InfoConst.PRINT_TEMPLATE_SALES_ORDER_PATH);
            PrintTemplate printTemplate = new PrintTemplate();
            printTemplate.setBranchId(branchId);
            printTemplate.setName("销售单");
            printTemplate.setContent(content);
            printTemplate.setTheType(InfoConst.PRINT_TEMPLATE_SALES_ORDER_TYPE);
            this.saveOrUpdatePrintTemplate(printTemplate);
        }
        if(this.getPrintTemplateListByType(branchId,InfoConst.PRINT_TEMPLATE_DEPOSIT_PAPER_TYPE).size()==0) {
            String content = this.readFile(InfoConst.PRINT_TEMPLATE_DEPOSIT_PAPER_PATH);
            PrintTemplate printTemplate = new PrintTemplate();
            printTemplate.setBranchId(branchId);
            printTemplate.setName("充值单");
            printTemplate.setContent(content);
            printTemplate.setTheType(InfoConst.PRINT_TEMPLATE_DEPOSIT_PAPER_TYPE);
            this.saveOrUpdatePrintTemplate(printTemplate);
        }

    }*/

    public String readFile(String path) {

        StringBuilder stringBuilder = new StringBuilder();
        try {

            Resource jmenuResourse = new ClassPathResource(path);
            InputStream io = jmenuResourse.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(io, "UTF-8");
            BufferedReader input = new BufferedReader(inputStreamReader);
            String text;
            while ((text = input.readLine()) != null)
                stringBuilder.append(text);
            io.close();
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return stringBuilder.toString();
    }

}
