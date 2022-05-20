package com.rgbplace.common.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

public class DateUtils {

    public static String getDateString(java.util.Date date) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(date);
    }

    public static LocalDateTime now() {
        return LocalDateTime.now();
    }
}
