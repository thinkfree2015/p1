package com.ming800.core.p.controller;

import com.ming800.core.base.service.BaseManager;
import com.ming800.core.does.model.Do;
import com.ming800.core.does.service.impl.DoManagerImpl;
import com.ming800.core.p.PConst;
import com.ming800.core.p.model.UserDefinedPortlet;
import com.ming800.core.p.model.Xentity;
import com.ming800.core.util.AuthorizationUtil;
import com.ming800.core.util.ComparatorDo;
import com.ming800.organization.OrganizationConst;
import com.ming800.organization.model.BigUser;
import com.ming800.organization.model.Permission;
import com.ming800.organization.model.Role;
import com.ming800.organization.service.PermissionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: ming
 * Date: 12-12-13
 * Time: 上午11:44
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/userDefinedPortlet")
public class UserDefinedPortletController {
    @Autowired
    private BaseManager baseManager;
    @Autowired
    private PermissionManager permissionManager;

    @RequestMapping("/formUserDefinedPortlet1.do")
    public ModelAndView formUserDefinedPortlet1(HttpServletRequest request, ModelMap modelMap) {

        List<Do> doList = new ArrayList<>();
//        Role role = AuthorizationUtil.getMyUser().getRole();
        for (Do queryModelDo : DoManagerImpl.getQueryModelMap().values()) {
            if (queryModelDo.getType().equals("portlet")) {
//                if (role.getSuperPermission().intValue() == OrganizationConst.ROLE_SUPER_PERMISSION_TRUE) {
//                    doList.add(queryModelDo);
//                }else{
                Xentity xentity = queryModelDo.getXentity();
                if ((xentity.getBasic() == null || xentity.getBasic().equals("")) && (xentity.getOthers() == null || xentity.getOthers().equals(""))) {
                    doList.add(queryModelDo);
                } else {
                    String queryModelDoName = queryModelDo.getName();
                    String url = "/basic/xm.do?qm=" + queryModelDoName + "_default";
                    String entityName = xentity.getName();
                    String tempOperations = permissionManager.convertUrlToOperation(url);
                    if (permissionManager.processAccess(entityName + ":" + tempOperations, null)) {
                        doList.add(queryModelDo);
                    }
                }
//                }
            }
        }
        ComparatorDo comparatorDo = new ComparatorDo();
        Collections.sort(doList, comparatorDo);
        modelMap.put("doList", doList);

        String resultPage = "/core/p/userDefinedPortletForm1";
        String resultType = request.getParameter("resultType");
        if (resultType != null && resultType.equals("tishi")) {
            modelMap.put("resultType", resultType);
            resultPage = "/core/p/definedPortletForm";
        }

        return new ModelAndView(resultPage);
    }


    @RequestMapping("/formUserDefinedPortlet.do")
    public ModelAndView formUserDefinedPortlet(HttpServletRequest request, ModelMap modelMap) {

        modelMap.put("resultType", request.getParameter("resultType"));

        modelMap.put("tempDoName", request.getParameter("tempDoName"));
        modelMap.put("tempDoLabel", request.getParameter("tempDoLabel"));

        String userDefinedPortletId = request.getParameter("id");
        if (userDefinedPortletId != null && !userDefinedPortletId.equals("")) {
            UserDefinedPortlet userDefinedPortlet = (UserDefinedPortlet) baseManager.getObject(UserDefinedPortlet.class.getName(), userDefinedPortletId);
            modelMap.put("userDefinedPortlet", userDefinedPortlet);
        }

        return new ModelAndView("/core/p/userDefinedPortletForm");
    }

    @RequestMapping("/saveOrUpdateUserDefinedPortlet.do")
    public ModelAndView saveOrUpdateUserDefinedPortlet(UserDefinedPortlet userDefinedPortlet) {

        if (userDefinedPortlet.getId() == null || userDefinedPortlet.getId().equals("")) {

            userDefinedPortlet.setUser(AuthorizationUtil.getUser());
            userDefinedPortlet.setTheStatus(PConst.USER_DEFINED_PORTLET_NORMAL);
            String portletLabel = DoManagerImpl.getQueryModelMap().get(userDefinedPortlet.getPortletName()).getLabel();
            userDefinedPortlet.setPortletLabel(portletLabel);

            /*document = ResourcesUtil.getDocument(PORTLET);
            List<Node> portletNodeList = document.selectNodes("/portlets/portlet");
            for (Node node : portletNodeList) {
                if (node.selectSingleNode("@name").getText().equals(userDefinedPortlet.getPortletName())) {
                    userDefinedPortlet.setUrl(node.selectSingleNode("@url").getText());
                }
            }*/

            userDefinedPortlet.setUrl("/basic/xm.do?qm=" + userDefinedPortlet.getPortletName() + "_default");

            //计算当前rowIndex  默认放在所在列的最后面
            String hql = "from UserDefinedPortlet u where u.columeIndex=:columeIdex and u.user.id=:userId order by u.rowIndex desc";
            LinkedHashMap<String, Object> queryParamMap = new LinkedHashMap<>();
            queryParamMap.put("columeIdex", userDefinedPortlet.getColumeIndex());
            queryParamMap.put("userId", userDefinedPortlet.getUser().getId());
            List<UserDefinedPortlet> userDefinedPortletList = baseManager.listObject(hql, queryParamMap);
            if (userDefinedPortletList != null && userDefinedPortletList.size() > 0) {
                userDefinedPortlet.setRowIndex(userDefinedPortletList.get(0).getRowIndex() + 1);
            } else {
                userDefinedPortlet.setRowIndex(0);
            }

            baseManager.saveOrUpdate(UserDefinedPortlet.class.getName(), userDefinedPortlet);
        } else {
            UserDefinedPortlet udp = (UserDefinedPortlet) baseManager.getObject(UserDefinedPortlet.class.getName(), userDefinedPortlet.getId());
            udp.setPortletName(userDefinedPortlet.getPortletName());
            String portletLabel = DoManagerImpl.getQueryModelMap().get(userDefinedPortlet.getPortletName()).getLabel();
            udp.setPortletLabel(portletLabel);
            udp.setUrl("/basic/xm.do?qm=" + userDefinedPortlet.getPortletName() + "_default");
            udp.setColumeIndex(userDefinedPortlet.getColumeIndex());
            udp.setHeight(userDefinedPortlet.getHeight());
            udp.setRowSize(userDefinedPortlet.getRowSize());
            baseManager.saveOrUpdate(UserDefinedPortlet.class.getName(), udp);
        }
        return new ModelAndView("redirect:/basic/xm.do?qm=plistUserDefinedPortlet_default");
    }

    @RequestMapping("/listMyDefinedPortlet.do")
    public ModelAndView listMyDefinedPortlet() {
        List<UserDefinedPortlet> myDefinedPortlet1 = new ArrayList<>(); //左边显示
        List<UserDefinedPortlet> myDefinedPortlet2 = new ArrayList<>(); //右边显示

        String queryStr = " from UserDefinedPortlet u where (u.user.id=:uId or u.role =:uRole) and u.theStatus!=0 order by u.rowIndex ";
        LinkedHashMap<String, Object> paramMap = new LinkedHashMap<>();
        paramMap.put("uId", AuthorizationUtil.getUser().getId());
        BigUser bigUser = (BigUser) baseManager.getObject(BigUser.class.getName(), AuthorizationUtil.getMyUser().getId());
//        paramMap.put("uRole", bigUser.getRole().getBasicType());
        List<UserDefinedPortlet> userDefinedPortletList = baseManager.listObject(queryStr, paramMap);

        ModelMap modelMap = new ModelMap();
        if (userDefinedPortletList != null && userDefinedPortletList.size() > 0) {
            for (UserDefinedPortlet userDefinedPortlet : userDefinedPortletList) {
                if (userDefinedPortlet.getColumeIndex() == 1) {
                    myDefinedPortlet1.add(userDefinedPortlet);
                    modelMap.put("myDefinedPortlet1", myDefinedPortlet1);
                } else if (userDefinedPortlet.getColumeIndex() == 2) {
                    myDefinedPortlet2.add(userDefinedPortlet);
                    modelMap.put("myDefinedPortlet2", myDefinedPortlet2);
                }
            }
        }
        return new ModelAndView("/core/p/myDefinedPortletList", modelMap);
    }

    @RequestMapping(value = "/saveMyDefinedPortlet.do")
    @ResponseBody
    public boolean saveMyDefinedPortlet(HttpServletRequest request) {
        String myDefinedPortlet = request.getParameter("myDefinedPortlet");
        if (myDefinedPortlet != null) {
            String[] columnPortletArray = myDefinedPortlet.split(";");
            int columnIndex = 1;
            for (String columnPortlet : columnPortletArray) {
                if (!columnPortlet.equals("")) {
                    int rowIndex = 0;
                    for (String rowPortlet : columnPortlet.split(",")) {
                        UserDefinedPortlet userDefinedPortlet = (UserDefinedPortlet) baseManager.getObject(UserDefinedPortlet.class.getName(), rowPortlet);
                        userDefinedPortlet.setColumeIndex(columnIndex);
                        userDefinedPortlet.setRowIndex(rowIndex);
                        baseManager.saveOrUpdate(UserDefinedPortlet.class.getName(), userDefinedPortlet);
                        rowIndex++;
                    }
                }
                columnIndex++;
            }
        }
        return true;
    }

}
