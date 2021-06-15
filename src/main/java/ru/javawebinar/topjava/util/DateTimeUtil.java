package ru.javawebinar.topjava.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static boolean isBetweenHalfOpen(LocalTime lt, LocalTime startTime, LocalTime endTime) {
        return lt.compareTo(startTime) >= 0 && lt.compareTo(endTime) < 0;
    }

    public static <T> boolean isBetweenHalfOpen(T current, T start, T end) {
        if (current instanceof LocalDate) {
            return ((LocalDate) current).compareTo((LocalDate) start) >= 0 && ((LocalDate) current).compareTo((LocalDate) end) <= 0;
        } else if (current instanceof LocalTime) {
            return ((LocalTime) current).compareTo((LocalTime) start) >= 0 && ((LocalTime) current).compareTo((LocalTime) end) <= 0;
        }
        return false;
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }
}

