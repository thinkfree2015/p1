package com.ming800.core.p.service.impl;

import com.ming800.core.p.model.Version;
import com.ming800.core.p.service.GlobalManager;
import com.ming800.core.p.service.VersionManager;
import com.ming800.core.util.AuthorizationUtil;
import com.ming800.core.util.ResourcesUtil;
import org.dom4j.Document;
import org.dom4j.Node;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ming
 * Date: 13-1-25
 * Time: 下午3:43
 * To change this template use File | Settings | File Templates.
 */

public class VersionManagerImpl implements VersionManager {

    private static final String VERSION_STANDARD = "/setting/version_standard.xml";
    private static final String VERSION_PROFESSIONAL = "/setting/version_professional.xml";
    private static final String VERSION_ADVANCE = "/setting/version_advance.xml";
    private static final String VERSION_EDU = "/setting/version_edu.xml";

    private static HashMap<String, Version> versionHashMap;
    @Autowired
    private GlobalManager globalManager;

    public static void loadVersion() {

        versionHashMap = new HashMap<>();

        versionHashMap.put("standard", createVersion(VERSION_STANDARD));
        versionHashMap.put("professional", createVersion(VERSION_PROFESSIONAL));
        versionHashMap.put("advance", createVersion(VERSION_ADVANCE));
        versionHashMap.put("edu", createVersion(VERSION_EDU));

    }

    public Version fetchVersion() {

        String versionName = "edu";
        if (versionName == null || versionName.equals("")) {
            Document document = globalManager.load();
            List<Node> nodeList = document.selectNodes("/global");
            versionName = nodeList.get(0).selectSingleNode("@version").getText();
        }

        return versionHashMap.get(versionName);
    }


    private static Version createVersion(String xmlPath) {

        Version tempVersion = new Version();

        Document document = load(xmlPath);
        if (document != null) {
            List<Node> versionNodeList = document.selectNodes("/version");

            for (Node versionNode : versionNodeList) {

                tempVersion.setName(versionNode.selectSingleNode("@name").getText());

                List<Node> moduleNodeList = document.selectNodes("/version/modules/module");
                LinkedList<String> moduleList = new LinkedList<>();
                LinkedList<String> entityList = new LinkedList<>();
                for (Node moduleNode : moduleNodeList) {
                    moduleList.add(moduleNode.selectSingleNode("@name").getText());

                    List<Node> entityNodeList = moduleNode.selectNodes("entity");
                    for (Node entityNode : entityNodeList) {
                        entityList.add(entityNode.selectSingleNode("@name").getText());
                    }
                }

                tempVersion.setModuleList(moduleList);
                tempVersion.setEntityList(entityList);
            }
        }

        return tempVersion;
    }

    public static Document load(String xmlPath) {
        return ResourcesUtil.getDocument(xmlPath);
    }
}
