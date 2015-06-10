package com.efeiyi.ec.orgnization.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by Administrator on 2015/5/28.
 */
@Entity
@Table(name="organization_tenant")
public class Tenant {
    private String id;
    private String name;
    private Consumer user;


    @Id
    @GenericGenerator(name = "id", strategy = "com.ming800.core.p.model.M8idGenerator")
    @GeneratedValue(generator = "id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name="name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    public Consumer getUser() {
        return user;
    }

    public void setUser(Consumer user) {
        this.user = user;
    }
}
