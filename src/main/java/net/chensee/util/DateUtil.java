package net.chensee.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    public static Date getNYRDateByStr(String dateStr){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = null;
        try {
            date1 = sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date1;
    }

    public static Date getNYRSFMDateByStr(String dateStr){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date1 = null;
        try {
            date1 = sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date1;
    }

    public static String getDateStr(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }

    public static String getDateNYRStr(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    public static String setDateDay(Date date,int value){
        Calendar calc =Calendar.getInstance();
        calc.setTime(date);
        calc.add(Calendar.DATE, value);
        Date minDate = calc.getTime();
        return getDateStr(minDate);
    }

    public static String setDateHour(Date date,int value){
        Calendar calc =Calendar.getInstance();
        calc.setTime(date);
        calc.add(Calendar.HOUR, value);
        Date minDate = calc.getTime();
        return getDateStr(minDate);
    }

    public static String setDateSecond(Date date,int value){
        Calendar calc =Calendar.getInstance();
        calc.setTime(date);
        calc.add(Calendar.SECOND, value);
        Date minDate = calc.getTime();
        return getDateStr(minDate);
    }
}
