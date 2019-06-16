package com.lhs.musiclab.pojo;

import java.io.Serializable;

public class MLabManager implements Serializable {
    private static final long serialVersionUID = -7459646454080908339L;
    private String mid;
    private String name;
    private String pwd;
    private String phone;
    private String email;
    //    1:ROOT权限,2:Manager权限
    private Integer role;
    private String head_img;

    @Override
    public String toString() {
        return "MLabManager{" +
                "mid='" + mid + '\'' +
                ", name='" + name + '\'' +
                ", pwd='" + pwd + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                ", head_img='" + head_img + '\'' +
                '}';
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public String getHead_img() {
        return head_img;
    }

    public void setHead_img(String head_img) {
        this.head_img = head_img;
    }
}
