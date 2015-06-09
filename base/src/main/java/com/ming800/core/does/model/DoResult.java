package com.ming800.core.does.model;

import com.ming800.organization.model.User;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: ming
 * Date: 12-11-10
 * Time: 上午9:59
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "core_do_result")
public class DoResult {

    private String id;

    private String label;
    private String tempQm;
    private String tempLabel;
    private String conditions;

    private String model;

    private Integer theStatus;

    private User user;
    private Date theDatetime;


    @Id
    @GenericGenerator(name = "id", strategy = "com.ming800.core.p.model.M8idGenerator")
    @GeneratedValue(generator = "id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "label")
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Column(name = "qm")
    public String getTempQm() {
        return tempQm;
    }

    public void setTempQm(String tempQm) {
        this.tempQm = tempQm;
    }

    @Column(name = "qm_label")
    public String getTempLabel() {
        return tempLabel;
    }

    public void setTempLabel(String tempLabel) {
        this.tempLabel = tempLabel;
    }

    @Column(name = "conditions")
    public String getConditions() {
        return conditions;
    }

    public void setConditions(String conditions) {
        this.conditions = conditions;
    }

    @Column(name = "thestatus")
    public Integer getTheStatus() {
        return theStatus;
    }

    public void setTheStatus(Integer theStatus) {
        this.theStatus = theStatus;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Column(name = "thedatetime")
    public Date getTheDatetime() {
        return theDatetime;
    }

    public void setTheDatetime(Date theDatetime) {
        this.theDatetime = theDatetime;
    }

    @Column(name = "model")
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
}
