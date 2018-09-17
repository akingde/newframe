package com.newframe.utils;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by tt on 7/18/18.
 */
public class DateUtils extends DateFormatUtils {

    /**
     * 获取utc时间
     *
     * @param date 时间
     * @param rule 日期格式
     * @throws ParseException
     * @author WangBin
     */
    public static Long getUTCTime(String date, String rule) throws ParseException {
        Long utcTime = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(rule);
            Date d = sdf.parse(date);
            utcTime = d.getTime();
        } catch (Exception e) {
            return utcTime;
        }
        return utcTime;
    }


    /**
     * 获取东八区北京时间
     *
     * @return
     */
    public static Long getTimeOfEastEight() {
        Long utcTime = null;
        TimeZone timeZone = TimeZone.getTimeZone("GMT+08:00");
        Date currentDate = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        df.setTimeZone(timeZone);
        try {
            utcTime = getUTCTime(df.format(currentDate), "yyyyMMddHHmmss");
        } catch (ParseException e) {
            return utcTime;
        }
        return utcTime;
    }

    public static void main(String[] args) {
        System.out.println(new Date());
        System.out.println(System.currentTimeMillis());
        System.out.println(getTimeOfEastEight());
    }
}
