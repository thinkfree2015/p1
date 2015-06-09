package com.ming800.core.p.service.impl;

import com.ming800.core.p.service.NotifyManager;
import com.ming800.core.util.AuthorizationUtil;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * User: ming
 * Date: 12-11-6
 * Time: 下午3:25
 * To change this template use File | Settings | File Templates.
 */
@Service
public class NotifyManagerImpl implements NotifyManager {
    // 判断密码是否尽心改过修改
    @Override
    public Boolean checkPasswordExpired() {
        return AuthorizationUtil.getMyUser().getPassword().equals("7c4a8d09ca3762af61e59520943dc26494f8941b");
    }

    // 检查licence是否合法
    @Override
    public Boolean checkLicenceLegal() {
        return false;
    }
}
