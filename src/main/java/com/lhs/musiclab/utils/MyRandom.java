package com.lhs.musiclab.utils;

import java.util.UUID;

public class MyRandom {
    public static String getUUID(){
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
