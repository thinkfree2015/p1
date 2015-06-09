package com.ming800.core.p.model;

import java.util.LinkedList;

/**
 * Created with IntelliJ IDEA.
 * User: ming
 * Date: 13-2-20
 * Time: 上午10:14
 * To change this template use File | Settings | File Templates.
 */
public class Version {

    private String name;

    private String title;
    private String index;
    private String initializeClass;

    private LinkedList<String> moduleList;
    private LinkedList<String> entityList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getInitializeClass() {
        return initializeClass;
    }

    public void setInitializeClass(String initializeClass) {
        this.initializeClass = initializeClass;
    }

    public LinkedList<String> getModuleList() {
        return moduleList;
    }

    public void setModuleList(LinkedList<String> moduleList) {
        this.moduleList = moduleList;
    }

    public LinkedList<String> getEntityList() {
        return entityList;
    }

    public void setEntityList(LinkedList<String> entityList) {
        this.entityList = entityList;
    }
}
