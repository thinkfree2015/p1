package com.ming800.core.p.model;

import com.ming800.organization.model.Branch;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 11-11-25
 * Time: 下午3:13
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "core_p_print_template")
@Inheritance(strategy = InheritanceType.JOINED)
public class PrintTemplate implements Serializable {
    @JsonProperty(value = "id")
    private String id;
    @JsonProperty(value = "text")
    private String name;
    private String content;
    private String theType;    //0 删除
    private Branch branch;

    public PrintTemplate() {
    }

    @Id
    @GenericGenerator(name = "templateId", strategy = "com.ming800.core.p.model.M8idGenerator")
    @GeneratedValue(generator = "templateId")
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

    @Column(name = "content")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Column(name = "thetype")
    public String getTheType() {
        return theType;
    }

    public void setTheType(String theType) {
        this.theType = theType;
    }

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id")
    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }
}
