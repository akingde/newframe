package com.newframe.utils;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.temporal.TemporalAdjusters;

/**
 * @author:wangdong
 * @description:时间处理的工具类
 */
public class TimeUtils {

    /**
     * 获取当月第一天的时间戳
     * @return
     */
    public static Integer getFirstDayOfMonth(){

        LocalDate localDate = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth());
        Long firstDayOfMonth = localDate.atStartOfDay().toEpochSecond(ZoneOffset.of("+8"));
        return Math.toIntExact(firstDayOfMonth);
    }

    public static Integer getLastDayOfMonth(){

        LocalDate localDate = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth());
        //这边需要在月底最后一天，如9.30 0:0:0 +86399L后得9.30 23:59:59
        Long lastDayOfMonth = localDate.atStartOfDay().toEpochSecond(ZoneOffset.of("+8"))+86399L;
        return Math.toIntExact(lastDayOfMonth);
    }
}
