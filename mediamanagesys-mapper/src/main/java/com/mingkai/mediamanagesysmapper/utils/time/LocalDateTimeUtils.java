package com.mingkai.mediamanagesysmapper.utils.time;

import com.dtstack.plat.lang.exception.BizException;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

/**
 * @description:  时间日期工具类
 * @author: Created by 云风 on 2019-04-04 11:05
 */
public class LocalDateTimeUtils {

    public static String DATE = "yyyy-MM-dd";

    public static String TIME = "HH:mm";

    public static String DATE_TIME  = "yyyy-MM-dd HH:mm";

    /**
     *  将字符串转换成制定格式的 LocalDateTime
     * @param dateTime
     * @param format
     * @return
     */
    public static LocalDateTime getLocalDateTimeFromStr(String dateTime,String format) {

        final DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .appendPattern(format)
                .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
                .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
                .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
                .parseDefaulting(ChronoField.MILLI_OF_SECOND, 0)
                .toFormatter();
        final LocalDateTime localDateTime = LocalDateTime.parse(dateTime, formatter);

        /*DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
        return LocalDateTime.parse(dateTime,dateTimeFormatter);*/

        return localDateTime;
    }

    /**
     *  将 LocalDateTime 转换成 format 格式字符串
     * @param localDateTime
     * @param format
     * @return
     */
   public static String formatLocalDateTime(LocalDateTime localDateTime,String format){
       DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
       return localDateTime.format(dateTimeFormatter);
   }

    /**
     * 获取两个LocalDateTime 的时间差
     * @param start
     * @param end
     * @return
     */
    public static Duration getDurationsBetween(LocalDateTime start,LocalDateTime end){

        return Duration.between(start,end);
    }


    /**
     * 得到电影时长
     * @param movieDuration
     * @return
     */
    public static Long getMovieDurationFormat(String movieDuration){
        // 得到第一个非数字的下标 get index of first nonNunber

        Integer index = movieDuration.length()-1;

        for (int i = 0;i < movieDuration.length();++i){
            if (movieDuration.charAt(i) < 48 || movieDuration.charAt(i) > 57){
                index = i-1;
            }
        }

        try{
            return Long.valueOf(movieDuration.substring(0,index));
        }catch (Exception e){
            throw new BizException("格式转换错误");
        }

    }

}
