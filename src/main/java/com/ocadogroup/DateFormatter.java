package com.ocadogroup;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;

public class DateFormatter {

    private static final SimpleDateFormat format = new SimpleDateFormat("HH:mm");

    private DateFormatter() {}

    public static String updateDate(String date, long numberOfMinutes) {
        Calendar calendar = getCalendar(date);
        calendar.add(Calendar.MINUTE, (int) numberOfMinutes);
        return format.format(calendar.getTime());
    }

    public static long getMinutesFromISOFormat(String isoString) {
        Duration duration = Duration.parse(isoString);
        return duration.toMinutes();
    }

    public static Calendar getCalendar(String date) {
        String[] splitDate = date.split(":");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(splitDate[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(splitDate[1]));
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }

    public static long numberOfMinutesBetweenTwoDates(String modifiedDate, String dateCompleteBy) {
        Calendar cal1 = getCalendar(modifiedDate);
        Calendar cal2 = getCalendar(dateCompleteBy);
        return (cal2.getTimeInMillis() - cal1.getTimeInMillis()) / (60 * 1000);
    }

}
