package com.urise.webapp.util;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;

public class DateUtil {
    public static final DateTimeFormatter TRIMMEDDAY = DateTimeFormatter.ofPattern("MM/yyyy");//.ISO_LOCAL_DATE;
    public static LocalDate of(int year, Month month) {
        return LocalDate.of(year, month, 1);
    }

    public static final LocalDate NOW = LocalDate.now();        //.of(3000, 1, 1);


}