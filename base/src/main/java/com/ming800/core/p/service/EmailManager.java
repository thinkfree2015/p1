package com.ming800.core.p.service;

import com.ming800.organization.model.BigUser;

/**
 * Created by IntelliJ IDEA.
 * User: ming
 * Date: 12-10-22
 * Time: 下午2:08
 * To change this template use File | Settings | File Templates.
 */
public interface EmailManager {

    public void sendRegisterEmail(BigUser bigUser);

    public Boolean activeUser(BigUser bigUser, String activeKey);

}
