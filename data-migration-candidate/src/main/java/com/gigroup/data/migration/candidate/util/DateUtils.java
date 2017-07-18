package com.gigroup.data.migration.candidate.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.util.StringUtils;

public class DateUtils {

	public static final String DEFAULT_TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";

	public static Date stringToDate(final String dateInString) {
		if (!StringUtils.hasText(dateInString))
			return null;
		return stringToDate(dateInString, DEFAULT_TIMESTAMP_FORMAT);
	}

	public static Date stringToDate(final String dateInString, final String format) {
		Date date = null;
		try {
			SimpleDateFormat formatter = new SimpleDateFormat(format);

			date = formatter.parse(dateInString);

		} catch (ParseException | IllegalArgumentException e) {
			e.printStackTrace();
		}
		return date;
	}

	public static String dateToString(final Date date, final String format) {
		DateFormat df = new SimpleDateFormat(format);
		return df.format(date);
	}

	public static Date addDays(Date date, int days) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, days);
		return cal.getTime();
	}
	
	public static Date addHours(Date date, int hours) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.HOUR, hours);
        return cal.getTime();
    }
}