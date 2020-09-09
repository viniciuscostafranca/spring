package io.vinicius.util;

import java.util.Calendar;
import java.util.Locale;
import static java.util.Calendar.*;
import java.util.Date;

public class DateUtil {

	public static int getDiffYears(Date first, Date last) {
		if (first != null && last != null) {
			Calendar a = getCalendar(first);
			Calendar b = getCalendar(last);
			int diff = b.get(YEAR) - a.get(YEAR);
			if (a.get(MONTH) > b.get(MONTH) || (a.get(MONTH) == b.get(MONTH) && a.get(DATE) > b.get(DATE))) {
				diff--;
			}
			return diff;
		}
		return 0;
	}

	public static Calendar getCalendar(Date date) {
		Calendar cal = Calendar.getInstance(Locale.US);
		cal.setTime(date);
		return cal;
	}
}
