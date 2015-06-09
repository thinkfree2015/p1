package com.ming800.core.develop.controller;

import com.ming800.core.base.service.BaseManager;
import com.ming800.core.develop.service.SplitBranchManager;
import com.ming800.organization.model.Branch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-11-8
 * Time: 上午10:55
 * To change this template use File | Settings | File Templates.
 */

@Controller
@RequestMapping("/spbranch")
public class SplitBranchController {

    @Autowired
    private SplitBranchManager splitBranchManager;


    @RequestMapping("/formSplitBranch.do")
    public ModelAndView formSplitBranch(HttpServletRequest request) {

        return new ModelAndView("/core/develop/splitBranch/splitBranchForm");
    }

    @RequestMapping("/checkData.do")
    @ResponseBody
    public String checkData(HttpServletRequest request) {
        String validatePassword = request.getParameter("validatePassword");
        if (validatePassword.equals("mingribo2002")) {
            String bname = request.getParameter("bname");
            return splitBranchManager.checkData(bname);
        } else {
            return "没有权限";
        }

    }


    @RequestMapping("/executeData.do")
    @ResponseBody
    public Boolean executeData(HttpServletRequest request) {

        String validatePassword = request.getParameter("validatePassword");
        if (validatePassword.equals("mingribo2002")) {
            String bname = request.getParameter("bname");
            String type = request.getParameter("type");

            return splitBranchManager.executeData(bname, type);
        } else {
            return false;
        }
    }

}
