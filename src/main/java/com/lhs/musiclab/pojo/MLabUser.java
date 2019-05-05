package com.lhs.musiclab.pojo;

import java.io.Serializable;
import java.util.Date;

public class MLabUser implements Serializable {
    private String uid;
    private String name;
    private String pwd;
    private String phone;
    //    1男，2女，3保密
    private Integer sex;
    //    1正常，2禁言，3黑名单
    private Integer access;
    private Date birthday;
    private String email;
    private String head_img;
    private String rqcode_img;
    private String blogbcg_img;


    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getAccess() {
        return access;
    }

    public void setAccess(Integer access) {
        this.access = access;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHead_img() {
        return head_img;
    }

    public void setHead_img(String head_img) {
        this.head_img = head_img;
    }

    public String getRqcode_img() {
        return rqcode_img;
    }

    public void setRqcode_img(String rqcode_img) {
        this.rqcode_img = rqcode_img;
    }

    public String getBlogbcg_img() {
        return blogbcg_img;
    }

    public void setBlogbcg_img(String blogbcg_img) {
        this.blogbcg_img = blogbcg_img;
    }

    @Override
    public String toString() {
        return "MLabUser{" +
                "uid='" + uid + '\'' +
                ", name='" + name + '\'' +
                ", pwd='" + pwd + '\'' +
                ", phone='" + phone + '\'' +
                ", sex=" + sex +
                ", access=" + access +
                ", birthday=" + birthday +
                ", email='" + email + '\'' +
                ", head_img='" + head_img + '\'' +
                ", rqcode_img='" + rqcode_img + '\'' +
                ", blogbcg_img='" + blogbcg_img + '\'' +
                '}';
    }
}
