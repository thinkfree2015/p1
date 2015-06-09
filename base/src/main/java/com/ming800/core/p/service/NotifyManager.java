package com.ming800.core.p.service;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-11-29
 * Time: 下午4:51
 * To change this template use File | Settings | File Templates.
 */
public interface NotifyManager {
    // 判断密码是否尽心改过修改
    Boolean checkPasswordExpired();

    // 检查licence是否合法
    Boolean checkLicenceLegal();
}
