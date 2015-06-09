package com.efeiyi.ec.purchase.model;

import com.efeiyi.ec.orgnization.model.User;
import com.efeiyi.ec.product.model.Product;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2015/5/28.
 */
@Entity
@Table(name="purchase_order")
public class PurchaseOrder {
    private String id;
    private String serial;
    private List<Product> purchaseProductList;
    private Date createDatetime;
    private String payWay;
    private User user;

    @Id
    @GenericGenerator(name = "id", strategy = "com.ming800.core.p.model.M8idGenerator")
    @GeneratedValue(generator = "id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name="serial")
    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    @OneToMany(fetch = FetchType.LAZY)
    public List<Product> getPurchaseProductList() {
        return purchaseProductList;
    }

    public void setPurchaseProductList(List<Product> purchaseProductList) {
        this.purchaseProductList = purchaseProductList;
    }

    @Column(name="create_datetime")
    public Date getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
    }

    @Column(name="payway")
    public String getPayWay() {
        return payWay;
    }

    public void setPayWay(String payWay) {
        this.payWay = payWay;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
