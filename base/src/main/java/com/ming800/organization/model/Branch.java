package com.ming800.organization.model;

 import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

/**
 * Created by IntelliJ IDEA.
 * User: ming
 * Date: 12-10-15
 * Time: 上午11:06
 * To change this template use File | Settings | File Templates.
 */

@Entity
@Table(name = "organization_branch")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class Branch implements Serializable {

    private String id;
    private String name;                //名称标识
    private String schoolName;         //学校名称
    private Integer theStatus;         // 正常，删除，停止，隐藏
    private Province province;
     private District district;
    //    private String deposit;
    private Date createDate;
    private BranchAttachment branchAttachment;

    private String version;
    private String setting;
    private String license;
    private Integer mode;          /**/

    private String serial;

    private List<BigUser> bigUserList;

    @Id
    @GenericGenerator(name = "id", strategy = "com.ming800.core.p.model.M8idGenerator")
    @GeneratedValue(generator = "id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "school_name")
    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    @Column(name = "the_status")
    public Integer getTheStatus() {
        return theStatus;
    }

    public void setTheStatus(Integer theStatus) {
        this.theStatus = theStatus;
    }

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "province_id")
    public Province getProvince() {
        return province;
    }

    public void setProvince(Province province) {
        this.province = province;
    }


    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "district_id")
    public District getDistrict() {
        return district;
    }

    public void setDistrict(District district) {
        this.district = district;
    }

/*    @Column(name = "deposit")
    public String getDeposit() {
        return deposit;
    }

    public void setDeposit(String deposit) {
        this.deposit = deposit;
    }*/

    @Column(name = "create_date")
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }


    @JsonIgnore
    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "attachment_id")
    public BranchAttachment getBranchAttachment() {
        return branchAttachment;
    }

    public void setBranchAttachment(BranchAttachment branchAttachment) {
        this.branchAttachment = branchAttachment;
    }

    @Column(name = "branch_version")
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Column(name = "setting")
    public String getSetting() {
        return setting;
    }

    public void setSetting(String setting) {
        this.setting = setting;
    }

    @Column(name = "license")
    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    @Column(name = "mode")
    public Integer getMode() {
        return mode;
    }

    public void setMode(Integer mode) {
        this.mode = mode;
    }

    @Column(name = "serial")
    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }


    public String generateSerial() {

        Double numIds = Math.random();
        return numIds.toString().substring(2, 8);
    }

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "branch")
    @OrderBy(" createDatetime asc ")
    public List<BigUser> getBigUserList() {
        return bigUserList;
    }

    public void setBigUserList(List<BigUser> bigUserList) {
        this.bigUserList = bigUserList;
    }
}
