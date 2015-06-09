package com.ming800.organization.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "organization_user_action")
public class BigUserAction {


    private String id;

    private String userId;

    private Integer theStatus;        // 0删除  1 等待验证， 2 已经验证
    private Integer theType;          //  -1 登录帐号激活      1 邮箱找回密码     2 手机找回密码
    private Integer theCode;             //  手机验证时候  六位随机验证码

    private Date theDatetime;         //  验证日期，  是否过期  24小时


    public BigUserAction() {
    }

    @Id
    @GenericGenerator(name = "id", strategy = "com.ming800.core.p.model.M8idGenerator")
    @GeneratedValue(generator = "id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "user_id")
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Column(name = "the_status")
    public Integer getTheStatus() {
        return theStatus;
    }

    public void setTheStatus(Integer theStatus) {
        this.theStatus = theStatus;
    }

    @Column(name = "the_type")
    public Integer getTheType() {
        return theType;
    }

    public void setTheType(Integer theType) {
        this.theType = theType;
    }

    @Column(name = "the_code")
    public Integer getTheCode() {
        return theCode;
    }

    public void setTheCode(Integer theCode) {
        this.theCode = theCode;
    }

    @Column(name = "the_datetime")
    public Date getTheDatetime() {
        return theDatetime;
    }

    public void setTheDatetime(Date theDatetime) {
        this.theDatetime = theDatetime;
    }

}
