package com.ming800.core.p.service;

import com.ming800.core.p.model.PrintTemplate;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-11-21
 * Time: 上午9:52
 * To change this template use File | Settings | File Templates.
 */
public interface PrintTemplateManager {
    PrintTemplate getPrintTemplate(String printTemplateId);

    List<PrintTemplate> getPrintTemplateList(String branchId, String[] theTypeArray);

    void saveOrUpdatePrintTemplate(PrintTemplate printTemplate);
}
