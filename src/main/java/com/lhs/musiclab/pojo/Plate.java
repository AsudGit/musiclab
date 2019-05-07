package com.lhs.musiclab.pojo;

import java.util.HashMap;
import java.util.Map;

public class Plate {
    public static final Map values = new HashMap<Integer, String>();
    static {
        values.put(1, "古典");
        values.put(2, "爵士");
        values.put(3, "流行");
        values.put(4, "民谣");
        values.put(5, "电子");
        values.put(6, "嘻哈");
        values.put(7, "轻音乐");
        values.put(8, "布鲁斯");
        values.put(9, "摇滚");
    }
}
