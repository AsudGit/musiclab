package com.lhs.musiclab.pojo;

import java.io.Serializable;

public class BlogItem implements Serializable,Comparable<BlogItem>{
    private static final long serialVersionUID = -5621163100674874023L;
    private Blog blog;
    private MLabUser mLabUser;

    @Override
    public int compareTo(BlogItem blogItem) {
        Blog o = blogItem.getBlog();
        /*防止忘记:
         * 0     表示两个对象相等
         * -1 后面的对象大于前面的对象
         * 1  后面的对象小于前面的对象
          if(this==o){
            return 0;
        }else if(this>o){
            return 1;
        }else if(this<o){
            return -1;
        }*/
        Integer thispower = blog.getViews() * 2 + blog.getLikes() * 4 + blog.getComments() * 4;
        Integer opower = o.getViews() * 2 + o.getLikes() * 4 + o.getComments() * 4;
        return opower-thispower;
    }

    @Override
    public String toString() {
        return "BlogItem{" +
                "blog=" + blog +
                ", mLabUser=" + mLabUser +
                '}';
    }


    public Blog getBlog() {
        return blog;
    }

    public void setBlog(Blog blog) {
        this.blog = blog;
    }

    public MLabUser getmLabUser() {
        return mLabUser;
    }

    public void setmLabUser(MLabUser mLabUser) {
        this.mLabUser = mLabUser;
    }
}
