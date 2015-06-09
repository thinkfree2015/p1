package com.ming800.core.p.model;

import com.ming800.organization.model.User;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: ming
 * Date: 12-12-13
 * Time: 上午11:15
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "core_p_user_defined_portlet")
public class UserDefinedPortlet {
    private String id;
    private Integer theStatus;
    private String portletName;
    private String portletLabel;
    private String url;
    private Integer rowSize;
    private Integer height;
    private Integer rowIndex;       //在第几个显示
    private Integer columeIndex;   // 左右  1,2
    private User user;

    private String role;


    @Id
    @GenericGenerator(name = "id", strategy = "com.ming800.core.p.model.M8idGenerator")
    @GeneratedValue(generator = "id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "colume_index")
    public Integer getColumeIndex() {
        return columeIndex;
    }

    public void setColumeIndex(Integer columeIndex) {
        this.columeIndex = columeIndex;
    }

    @Column(name = "height")
    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    @Column(name = "portlet_name")
    public String getPortletName() {
        return portletName;
    }

    public void setPortletName(String portletName) {
        this.portletName = portletName;
    }

    @Column(name = "portlet_label")
    public String getPortletLabel() {
        return portletLabel;
    }

    public void setPortletLabel(String portletLabel) {
        this.portletLabel = portletLabel;
    }

    @Column(name = "row_index")
    public Integer getRowIndex() {
        return rowIndex;
    }

    public void setRowIndex(Integer rowIndex) {
        this.rowIndex = rowIndex;
    }

    @Column(name = "row_size")
    public Integer getRowSize() {
        return rowSize;
    }

    public void setRowSize(Integer rowSize) {
        this.rowSize = rowSize;
    }

    @Column(name = "thestatus")
    public Integer getTheStatus() {
        return theStatus;
    }

    public void setTheStatus(Integer theStatus) {
        this.theStatus = theStatus;
    }

    @Column(name = "url")
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Column(name = "role")
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
