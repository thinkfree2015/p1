package com.ming800.core.util;

import com.ming800.organization.model.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;


/**
 * @author WuYingbo
 */
public class AuthorizationUtil {


    public static MyUser getMyUser() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        try {
            return (MyUser) authentication.getPrincipal();
        } catch (Exception e) {
            MyUser myUser=new MyUser();
            Role role=new Role();
            role.setBasicType("all");

            myUser.setRole(role);
            myUser.setCityShotType("headquarters");
            return myUser;


        }
    }



    public static User getUser() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        MyUser myUser = (MyUser) authentication.getPrincipal();
        User user = new User();
        user.setId(myUser.getId());
        user.setName(myUser.getName());
        user.setUsername(myUser.getUsername());
        return user;
    }

    public static String getRoleType(){
        MyUser myUser = getMyUser();
        if(myUser!=null&&myUser.getId()!=null){
            return myUser.getRole().getBasicType();
        }
        else{
            return "";
        }
    }

    //获得当前学生用户
//    public static StudentUser getStudentUser() {
//        SecurityContext securityContext = SecurityContextHolder.getContext();
//        Authentication authentication = securityContext.getAuthentication();
//        try {
//            return (StudentUser) authentication.getPrincipal();
//        } catch (Exception e) {
//            return new StudentUser();
//        }
//    }




}
