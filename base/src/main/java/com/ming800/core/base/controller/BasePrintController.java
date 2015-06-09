package com.ming800.core.base.controller;

import com.ming800.core.does.model.Do;
import com.ming800.core.does.service.DoHandler;
import com.ming800.core.does.service.DoManager;
import com.ming800.core.p.service.PrintTemplateManager;
import com.ming800.core.util.ReflectUtil;
import freemarker.core.Environment;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.Writer;
import java.util.Locale;
import java.util.Map;


/**
 * Created by IntelliJ IDEA.
 * User: ming
 * Date: 12-10-24
 * Time: 下午1:54
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/basic")
public class BasePrintController {

    @Autowired
    private DoManager doManager;

    @RequestMapping("/xmp.do")
    public ModelAndView xmp(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) throws Exception {

        String qm = request.getParameter("qm");
        Do tempDo = doManager.getDoByQueryModel(qm);

        modelMap = execute(tempDo, modelMap, request);

        basePrint(ReflectUtil.invokeGetterMethod(modelMap.get("printTemplate"), "content").toString(), modelMap, response);

        return null;
    }

    /**
     * 打印
     *
     * @param printTemplateContent 打印模板的主体内容
     * @param propMap              存放参数
     */
    public void basePrint(String printTemplateContent, Map<String, Object> propMap, HttpServletResponse response) {
        PrintWriter printWriter = null;
        String templateStr = "[#ftl]";                    //用于将freeMaker 中默认的<>标准  转为[]标准 解决在ckEditor中解析冲突的问题
        templateStr += printTemplateContent;
        Configuration cfg = new Configuration();
        cfg.setTemplateExceptionHandler(new MyTemplateExceptionHandler());
        try {
            Template t = new Template("name", new StringReader(templateStr), cfg);
            response.setCharacterEncoding("UTF-8");
            response.setLocale(Locale.CHINA);
            response.setContentType("text/html");
            printWriter = response.getWriter();
            printWriter.println("<html> <body onload=\'print();\'>");
            t.process(propMap, printWriter);
            printWriter.println("</body></html>");
        } catch (TemplateException e) {
        } catch (IOException e) {
        } finally {
            try {
                if (printWriter != null) {
                    printWriter.flush();
                    printWriter.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    class MyTemplateExceptionHandler implements TemplateExceptionHandler {
        @Override
        public void handleTemplateException(TemplateException e, Environment environment, Writer writer) throws TemplateException {
            try {
//                System.out.print(e.getMessage());
//                throw new Exception(e.getMessage());
            } catch (Exception e1) {
                throw new TemplateException("Failed to print error message. Cause:" + e, environment);
            }
        }
    }

    private ModelMap execute(Do tempDo, ModelMap modelMap, HttpServletRequest request) throws Exception {

        DoHandler doHandler = (DoHandler) Class.forName(tempDo.getExecute()).newInstance();

        return doHandler.handle(modelMap, request);
    }
}
