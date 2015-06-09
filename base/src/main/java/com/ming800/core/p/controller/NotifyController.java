package com.ming800.core.p.controller;

import com.ming800.core.p.model.Notify;
import com.ming800.core.p.service.NotifyManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ming
 * Date: 12-11-6
 * Time: 下午2:32
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/notify")
public class NotifyController {

    @Autowired
    private NotifyManager notifyManager;

    @RequestMapping("/checkUserMess.do")
    @ResponseBody
    public List checkUserMess(HttpServletRequest request) {
//        notifyManager.checkLicenceLegal();
        notifyManager.checkPasswordExpired();
        List<Notify> notifyList = new ArrayList<>();
        Notify passwordNotify = new Notify();
        passwordNotify.setTheType(1);
        passwordNotify.setNote1("为了您的账号安全，请及时修改密码");
        passwordNotify.setUrl1("/user/formUser.do");
        notifyList.add(passwordNotify);
        return notifyList;
    }

}
