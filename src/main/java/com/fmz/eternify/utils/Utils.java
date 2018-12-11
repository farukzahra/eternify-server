package com.fmz.eternify.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    public static String splitURL(String url) {
        try {
            return url.split("\\.com/")[1];
        } catch (Exception e) {}

        return null;
    }

    public static String dateToString(Date data, String pattern) {
        String dataStr = null;
        if (data != null) {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            dataStr = sdf.format(data);
        }
        return dataStr;
    }

    public static Date stringToDate(String dataStr) {
        return stringToDate(dataStr, "yyyy-MM-dd HH:mm");
    }

    public static Date stringToDate(String dataStr, String pattern) {
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        String dateInString = dataStr;
        Date date;
        try {
            date = formatter.parse(dateInString);
            return date;
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

}
