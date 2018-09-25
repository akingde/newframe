package com.newframe.controllers.api;

import javax.persistence.criteria.CriteriaBuilder;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;

/**
 * @author:wangdong
 * @description:
 */
public class TestBigDecimal {

    public static void main(String[] args) {

        BigDecimal a = new BigDecimal(100.55);
        BigDecimal b = new BigDecimal(110.66);
        System.out.println(a.compareTo(b));
        System.out.println(b.compareTo(a));
        LocalDate today = LocalDate.now();
        LocalDate time = today.plus(1, ChronoUnit.MONTHS);
        Long uix = time.atStartOfDay().toEpochSecond(ZoneOffset.of("+8"));
        Long uixTime = LocalDate.now().plus(1, ChronoUnit.MONTHS).atStartOfDay().toEpochSecond(ZoneOffset.of("+8"));

        Integer hh = Math.toIntExact(uixTime);
        System.out.println(hh);
        System.out.println(uix);
        System.out.println(uixTime);

        LocalDateTime dateTime = LocalDateTime.now();
        dateTime.atOffset(ZoneOffset.of("+8"));
        LocalDateTime localDateTime = dateTime.plus(1,ChronoUnit.MONTHS);
        System.out.println(localDateTime);
        System.out.println(today);
        LocalDate nextDay = today.plus(1, ChronoUnit.MONTHS);
        LocalDate nextDay2 = today.plus(2, ChronoUnit.MONTHS);
        LocalDate nextDay3 = today.plus(1, ChronoUnit.MONTHS);
        LocalDate nextDay4 = today.plus(1, ChronoUnit.MONTHS);
        LocalDate nextDay5 = today.plus(1, ChronoUnit.MONTHS);
        LocalDate nextDay6 = today.plus(1, ChronoUnit.MONTHS);
        LocalDate nextDay7 = today.plus(1, ChronoUnit.MONTHS);
        System.out.println(nextDay);
        System.out.println(nextDay2);

        boolean result = a.compareTo(b) == -1;
        System.out.println();
    }
}
