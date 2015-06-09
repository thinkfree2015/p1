package com.ming800.core.taglib;

import com.ming800.core.p.service.VersionManager;
import com.ming800.core.p.service.impl.VersionManagerImpl;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-12-1
 * Time: 下午2:10
 * To change this template use File | Settings | File Templates.
 */

public class Version extends TagSupport {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int doStartTag() throws JspException {
        if (processVersion()) {
            return 1;
        } else {
            return 0;
        }
    }

    private boolean processVersion() {
        VersionManager versionManager = new VersionManagerImpl();
        String version = versionManager.fetchVersion().getName();
        for (String n : name.split(",")) {
            if (n.equals(version)) {
                return true;
            }
        }
        return false;
    }
}