package com.lhs.musiclab.pojo;

import java.io.Serializable;

public class BlogItem implements Serializable,Comparable<BlogItem>{
    private static final long serialVersionUID = 2210633944333215245L;
    private Blog blog;
    private BlogCount blogCount;
    private MLabUser mLabUser;
    private boolean star;
    private boolean like;

    @Override
    public int compareTo(BlogItem blogItem) {
        BlogCount o = blogItem.getBlogCount();
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
        Integer thispower = blogCount.getViews() * 2 + blogCount.getLikes() * 4 + blogCount.getComments() * 4;
        Integer opower = o.getViews() * 2 + o.getLikes() * 4 + o.getComments() * 4;
        return opower-thispower;
    }

    @Override
    public String toString() {
        return "BlogItem{" +
                "blog=" + blog +
                ", blogCount=" + blogCount +
                ", mLabUser=" + mLabUser +
                ", star=" + star +
                ", like=" + like +
                '}';
    }

    public Blog getBlog() {
        return blog;
    }

    public void setBlog(Blog blog) {
        this.blog = blog;
    }

    public BlogCount getBlogCount() {
        return blogCount;
    }

    public void setBlogCount(BlogCount blogCount) {
        this.blogCount = blogCount;
    }

    public MLabUser getmLabUser() {
        return mLabUser;
    }

    public void setmLabUser(MLabUser mLabUser) {
        this.mLabUser = mLabUser;
    }

    public boolean isStar() {
        return star;
    }

    public void setStar(boolean star) {
        this.star = star;
    }

    public boolean isLike() {
        return like;
    }

    public void setLike(boolean like) {
        this.like = like;
    }
}
