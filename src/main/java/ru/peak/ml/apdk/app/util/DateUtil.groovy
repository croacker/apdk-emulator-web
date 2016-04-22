package ru.peak.ml.apdk.app.util
import org.joda.time.DateTimeComparator

import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
/**
 *
 */
class DateUtil {

    static boolean timeBefore(Date date1, Date date2) {
        int time1 = (int) (date1.getTime() % (24 * 60 * 60 * 1000L));
        int time2 = (int) (date2.getTime() % (24 * 60 * 60 * 1000L));
        int delta = (time1 - time2);
        return delta > 0 ? false : true;
    }

    static boolean timeAfter(Date date1, Date date2) {
        int time1 = (int) (date1.getTime() % (24 * 60 * 60 * 1000L));
        int time2 = (int) (date2.getTime() % (24 * 60 * 60 * 1000L));
        int delta = (time2 - time1);
        return delta > 0 ? false : true;
    }

    static boolean dateEquals(Date date1, Boolean date2) {
        DateTimeComparator.getDateOnlyInstance().compare(date1, date2) == 0;
    }

    static int dayOfWeek(Date date) {
        date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getDayOfWeek().getValue();
    }

    static Date endDayMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        return cal.getTime();
    }

    static LocalTime toLocalTime(Date date){
        LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()).toLocalTime();
    }

    static Date now() {
        new Date();
    }
}
