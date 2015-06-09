package com.ming800.core.p.controller;

import com.ming800.core.p.PConst;
import com.ming800.core.p.model.PrintTemplate;
import com.ming800.core.p.service.PrintTemplateManager;
import com.ming800.core.util.AuthorizationUtil;
import com.ming800.core.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: han
 * Date: 12-2-14
 * Time: 下午2:39
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/printTemplate")
public class PrintTemplateAction {
    @Autowired
    protected PrintTemplateManager printTemplateManager;

    //合同模版保存
    @RequestMapping("/formPrintTemplate.do")
    public ModelAndView formPrintTemplate(String printTemplateId, ModelMap modelMap) {
        if (printTemplateId != null) {
            PrintTemplate printTemplate = printTemplateManager.getPrintTemplate(printTemplateId);
            modelMap.put("printTemplate", printTemplate);
        }
        return new ModelAndView("/oa/info/printTemplate/printTemplateForm", modelMap);
    }

    //恢復默認設置
    @RequestMapping("/resetPrintTemplate.do")
    public ModelAndView resetPrintTemplate(HttpServletRequest request, ModelMap modelMap) throws Exception {
        PrintTemplate printTemplate = new PrintTemplate();
        if (StringUtils.isNotEmpty(request.getParameter("id"))) {
            printTemplate = printTemplateManager.getPrintTemplate(request.getParameter("id"));
        }
        String printTemplateType = request.getParameter("printTemplateType");
        printTemplate.setTheType(printTemplateType);
        printTemplate.setName(request.getParameter("name"));
        String filePath = "";
        if (printTemplateType.equals(PConst.PRINT_TEMPLATE_CHARGE_ORDER)) {
            filePath = "/setting/printTemplate/printTemplateChargeOrder.html";
        } else if (printTemplateType.equals(PConst.PRINT_TEMPLATE_LISTEN_COURSE_CARD)) {
            filePath = "/setting/printTemplate/printTemplateStudentPaper.html";
        } else if (printTemplateType.equals(PConst.PRINT_TEMPLATE_STUDENT_ROLL)) {
            filePath = "/setting/printTemplate/printTemplateStudentRoll.html";
        } else if (printTemplateType.equals(PConst.PRINT_TEMPLATE_CHARGE_ORDER_CZ)) {
            filePath = "/setting/printTemplate/printTemplateChargeOrderCZ.html";
        } else {
            throw new Exception("模板不存在");
        }
        Resource res = new ClassPathResource(filePath);
        String content = StringUtil.inputStream2String(res.getInputStream());
        printTemplate.setContent(content);
        modelMap.put("printTemplate", printTemplate);
        return new ModelAndView("/oa/info/printTemplate/printTemplateForm", modelMap);
    }


    //合同模版保存
    @RequestMapping("/saveOrUpdatePrintTemplate.do")
    public ModelAndView saveOrUpdatePrintTemplate(PrintTemplate printTemplate) {
        ModelMap modelMap = new ModelMap();
//        printTemplate.setContent(this.content);

        this.printTemplateManager.saveOrUpdatePrintTemplate(printTemplate);
        return new ModelAndView("redirect:/basic/xm.do?qm=plistPrintTemplateSetting_default", modelMap);
    }

    /**
     * 恢复原始模板
     * @return
     */
  /*  public String resetPrintTemplate(){
        this.printTemplate = this.printTemplateManager.getPrintTemplateById(printTemplate.getId());
        String content="";
        switch (printTemplate.getTheType()) {
            case 101:
                content = printTemplateManager.readFile(InfoConst.PRINT_TEMPLATE_CHARGE_ORDER_PATH);
                break;
            case 103:
                content = printTemplateManager.readFile(InfoConst.PRINT_TEMPLATE_STUDENT_PAPER_PATH);
                break;
            default:break;
        }

        if(content.equals("")) {

        }else{
            printTemplate.setContent(content);
            this.printTemplateManager.saveOrUpdatePrintTemplate(printTemplate);
        }
        return SUCCESS;
    }
*/

}
