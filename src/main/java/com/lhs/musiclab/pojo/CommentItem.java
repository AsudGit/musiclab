package com.lhs.musiclab.pojo;

import java.io.Serializable;
import java.util.Arrays;

/***
 * 博客详情页的评论项
 */
public class CommentItem implements Serializable {

    private static final long serialVersionUID = 3159713760352647582L;
    private Comment comment;
    private MLabUser mLabUser;

    @Override
    public String toString() {
        return "CommentItem{" +
                "comment=" + comment +
                ", mLabUser=" + mLabUser +
                '}';
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public MLabUser getmLabUser() {
        return mLabUser;
    }

    public void setmLabUser(MLabUser mLabUser) {
        this.mLabUser = mLabUser;
    }
}
