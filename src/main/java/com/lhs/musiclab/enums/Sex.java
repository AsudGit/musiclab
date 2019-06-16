package com.lhs.musiclab.enums;

import java.util.HashMap;
import java.util.Map;

public class Sex {
    public static final Map values = new HashMap<Integer, String>();
    static {
        values.put(1, "男");
        values.put(2, "女");
        values.put(3, "保密");
    }
}
