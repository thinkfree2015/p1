package com.ming800.organization;

import com.ming800.core.util.AuthorizationUtil;
import com.ming800.organization.model.MyUser;
import com.ming800.organization.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by hean on 2014/8/4.
 */
public class LoginOutSuccessHandler extends
        SimpleUrlLogoutSuccessHandler implements LogoutSuccessHandler {


    @Override
    public void onLogoutSuccess
            (HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        if (authentication != null) {
            MyUser myUser = (MyUser) authentication.getPrincipal();
            if (myUser != null && myUser.getRole() != null) {
                if ("photographer,consumer".contains(myUser.getRole().getBasicType())) {
                    response.sendRedirect(request.getContextPath() + "/pc/home.do");
                } else if ("admin,manager,agent,dresser,fixer,designer".contains(myUser.getRole().getBasicType()) || myUser.getRole().getBasicType().contains("Manager")) {
                    response.sendRedirect(request.getContextPath() + "/manage/index.do");
                }
            }
        }

        super.onLogoutSuccess(request, response, authentication);
    }
}

