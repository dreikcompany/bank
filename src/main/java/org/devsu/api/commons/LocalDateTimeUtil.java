package org.devsu.api.commons;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class LocalDateTimeUtil {

    private LocalDateTimeUtil() {}

    public static LocalDateTime ignoreTime(LocalDateTime localDateTime) {
        return localDateTime.withHour(0).withMinute(0).withSecond(1);
    }

    public static LocalDateTime toLastSecond(LocalDateTime localDateTime) {
        return localDateTime.with(LocalTime.of(23, 59, 59));
    }

    public static LocalDateTime ignoreTime(LocalDate localDate) {
        return localDate.atTime(0, 0, 0);
    }

    public static LocalDateTime toLastSecond(LocalDate localDate) {
        return localDate.atTime(LocalTime.of(23, 59, 59));
    }

    public static void validateDateAfter(LocalDate initialDate, LocalDate endDate) {
        if (!endDate.isAfter(initialDate))
            throw new IllegalArgumentException("Fecha fin debe ser posterior a Fecha inicio.");
    }
}
