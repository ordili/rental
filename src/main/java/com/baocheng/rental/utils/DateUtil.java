package com.baocheng.rental.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;

/**
 * @author pengboran
 */
public class DateUtil {

    public static String now() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String nowString = dateTimeFormatter.format(now);
        System.out.println(nowString);
        return nowString;
    }

    public static String now2() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime ldt = LocalDateTime.of(2020, 7, 23, 0, 0);
        String nowString = dateTimeFormatter.format(now);
        System.out.println("now is: " + nowString);
        System.out.println(now.isAfter(ldt));
        System.out.println(java.time.Duration.between(ldt, now).toDays());

        return "";
    }

    public static void t3() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate now = LocalDate.now();

        LocalDate before = LocalDate.of(2020, 07, 22);
        System.out.println(now.isAfter(before));
        System.out.println(dateTimeFormatter.format(now));
        System.out.println(Period.between(before, now).getDays());
    }

    public static void main(String[] args) throws Exception {

        t3();
    }
}
