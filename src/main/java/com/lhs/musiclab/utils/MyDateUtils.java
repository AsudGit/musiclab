package com.lhs.musiclab.utils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

public class MyDateUtils {
    /***
     * 判断年月日是否相等
     * @param d1
     * @param d2
     * @return 执行结果
     */
    public static boolean sameDate(Date d1,Date d2){
        LocalDate localDate1 = ZonedDateTime.ofInstant(d1.toInstant(), ZoneId.systemDefault()).toLocalDate();
        LocalDate localDate2 = ZonedDateTime.ofInstant(d2.toInstant(), ZoneId.systemDefault()).toLocalDate();
        return localDate1.isEqual(localDate2);
    }
}
