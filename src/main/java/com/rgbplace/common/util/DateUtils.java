package com.rgbplace.common.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class DateUtils {

    public static String getDateString(java.util.Date date) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(date);
    }
}
