package com.quazaru.plannter.dateTimeHandler;

import java.util.Calendar;

public class CalendarTimeFormatter {
    public static String formatTime(Calendar calendar) {
        StringBuilder result = new StringBuilder();
        result.append(calendar.getTime());



        return  result.toString();
    }
}
