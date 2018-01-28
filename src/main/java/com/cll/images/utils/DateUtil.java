package com.cll.images.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author chenliangliang
 * @date 2018/1/2
 */
public class DateUtil {

    public static String dateString() {
        LocalDate date = LocalDate.now();
        return date.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    }


    /**
     * 返回日期字符串，精确到毫秒
     * @return 日期字符串
     */
    public static String dateTimeString() {
        LocalDateTime dateTime = LocalDateTime.now();
        return dateTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));

    }

}
