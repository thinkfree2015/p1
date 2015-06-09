package com.ming800.core.develop.controller;

import com.ming800.core.xdo.controller.BaseController;
import com.ming800.core.util.AuthorizationUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-12-3
 * Time: 下午3:02
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/remoteQuestion")
public class RemoteQuestionController extends BaseController {

    /**
     * 测试远程连接
     * branchName   学校标识
     * branchSchoolName 学校名称
     */
    @RequestMapping("/listMyQuestion.do")
    public ModelAndView listMyQuestion(ModelMap modelMap) throws Exception {

        modelMap.put("userId", AuthorizationUtil.getMyUser().getId());


        return new ModelAndView("/core/develop/question/questionList");
    }
}
