package com.lhs.musiclab.enums;

public enum Plate {
    CLASSICAL( "古典",1),JAZZ("爵士",2),POP("流行",3),BALLAD("民谣",4),ELECTRONIC("电子",5),
    HIPHOP("嘻哈",6),LIGHTMUSIC("轻音乐",7),BLUS("布鲁斯",8), ROCK("摇滚", 9);

    private String value;
    private int index;

    private Plate(String value,int index){
        this.value = value;
        this.index = index;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
