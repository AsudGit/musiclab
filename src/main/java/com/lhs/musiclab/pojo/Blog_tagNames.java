package com.lhs.musiclab.pojo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Arrays;

public class Blog_tagNames implements Serializable {
    private static final long serialVersionUID = -3198998212856800005L;
    private Blog blog;
    private String[] names;

    @Override
    public String toString() {
        return "Blog_tagNames{" +
                "blog=" + blog +
                ", names=" + Arrays.toString(names) +
                '}';
    }

    public Blog getBlog() {
        return blog;
    }

    public void setBlog(Blog blog) {
        this.blog = blog;
    }

    public String[] getNames() {
        return names;
    }

    public void setNames(String[] names) {
        this.names = names;
    }
}
