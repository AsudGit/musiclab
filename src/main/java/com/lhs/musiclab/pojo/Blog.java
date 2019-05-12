package com.lhs.musiclab.pojo;

import java.io.Serializable;
import java.sql.Timestamp;

public class Blog implements Serializable,Comparable<Blog> {
    private String bid;
    private String title;
    private String content;
    private Timestamp blogged_time;
    private Integer views;
    private Integer likes;
    private Integer comments;
    //1:正常,2:置顶
    private Integer status;
    //板块号
    private Integer plate;
    //博主id
    private String uid;

    @Override
    public int compareTo(Blog o) {
        /*防止忘记:
         * 0     表示两个对象相等
         * -1 后面的对象大于前面的对象   降序
         * 1    前面小后面大升序
         */ /*if(this==o){
            return 0;
        }else if(this>o){
            return 1;
        }else if(this<o){
            return -1;
        }*/
        Integer thispower = this.views * 2 + this.likes * 4 + this.comments * 4;
        Integer opower = o.views * 2 + o.likes * 4 + o.comments * 4;
        return thispower-opower;
    }

    @Override
    public String toString() {
        return "Blog{" +
                "bid='" + bid + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", blogged_time=" + blogged_time +
                ", views=" + views +
                ", likes=" + likes +
                ", comments=" + comments +
                ", status=" + status +
                ", plate=" + plate +
                ", uid='" + uid + '\'' +
                '}';
    }

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getBlogged_time() {
        return blogged_time;
    }

    public void setBlogged_time(Timestamp blogged_time) {
        this.blogged_time = blogged_time;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public Integer getComments() {
        return comments;
    }

    public void setComments(Integer comments) {
        this.comments = comments;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getPlate() {
        return plate;
    }

    public void setPlate(Integer plate) {
        this.plate = plate;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
