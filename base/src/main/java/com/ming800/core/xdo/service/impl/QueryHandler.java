package com.ming800.core.xdo.service.impl;

import com.ming800.core.does.model.XQuery;
import org.springframework.ui.ModelMap;

import javax.servlet.http.HttpServletRequest;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-11-5
 * Time: 上午10:26
 * To change this template use File | Settings | File Templates.
 */
public interface QueryHandler {

    public XQuery handle(XQuery xQuery) throws Exception;

}
