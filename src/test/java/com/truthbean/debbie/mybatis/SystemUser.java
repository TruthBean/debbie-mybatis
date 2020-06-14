package com.truthbean.debbie.mybatis;

import java.util.Date;

/**
 * @author TruthBean/Rogar·Q
 * @since 0.0.2
 * Created on 2020-03-23 17:08
 */
public class SystemUser {

    private String id;

    private Date createTime;

    private String email;

    private String name;

    private String password;

    private String phone;

    private Date updateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "{" + "\"id\":\"" + id + "\"" + "," + "\"createTime\":" + createTime + "," + "\"email\":\"" + email + "\"" + "," + "\"name\":\"" + name + "\"" + "," + "\"password\":\"" + password + "\"" + "," + "\"phone\":\"" + phone + "\"" + "," + "\"updateTime\":" + updateTime + "}";
    }
}
