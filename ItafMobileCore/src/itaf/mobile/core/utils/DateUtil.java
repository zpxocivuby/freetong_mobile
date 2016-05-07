package itaf.mobile.core.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Date Utility Class. And please use Apache common DateUtils to add, judge
 * equality, round, truncate date.
 * 
 * @author sherlockq
 * @see org.apache.commons.lang.time.DateUtils
 */
public class DateUtil {

	/**
	 * DateFormat cache map
	 */
	protected static Map<String, DateFormat> dateFormatMap = new HashMap<String, DateFormat>();

	/**
	 * Date format pattern
	 */
	public static final String FORMAT_DATE_DEFAULT = "yyyy-MM-dd";
	public static final String FORMAT_DATE_YYYYMMDD = "yyyyMMdd";
	public static final String FORMAT_DATE_YYYY_M_D = "yyyy-M-d";
	public static final String FORMAT_DATE_YYYY_MM_DD = "yyyy-MM-dd";
	public static final String FORMAT_DATE_SLASH_YYYY_MM_DD = "yyyy/MM/dd";
	public static final String FORMAT_DATE_SLASH_YYYY_M_DD = "yyyy/M/dd";
	public static final String FORMAT_DATE_SLASH_YYYY_MM_D = "yyyy/MM/d";
	public static final String FORMAT_DATE_SLASH_YYYY_M_D = "yyyy/M/d";

	/**
	 * DateTime format pattern
	 */
	public static final String FORMAT_DATETIME_DEFAULT = "yyyy-MM-dd HH:mm:ss";
	public static final String FORMAT_DATETIME_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
	public static final String FORMAT_DATETIME_YYYY_MM_DD_HH_MM_SS_SSS = "yyyy-MM-dd HH:mm:ss.SSS";
	public static final String FORMAT_DATETIME_YYYY_MM_DD_HHMM = "yyyy-MM-dd HHmm";
	public static final String FORMAT_DATETIME_YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
	public static final String FORMAT_DATETIME_YYYY_MM_DD_HHMMSS = "yyyy-MM-dd HHmmss";
	public static final String FORMAT_DATETIME_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

	public static final String FORMAT_DATETIME_YYYYMMDDTHHMMSSZ = "yyyy-MM-dd'T'HH:mm:ssZ";
	public static final String FORMAT_DATETIME_YYYYMMDDTHHMMSS_SSSZ = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";

	/**
	 * Time format pattern
	 */
	public static final String FORMAT_TIME_DEFAULT = "HH:mm:ss";
	public static final String FORMAT_TIME_HH_MM = "HH:mm";
	public static final String FORMAT_TIME_HHMM = "HHmm";
	public static final String FORMAT_TIME_HH_MM_SS = "HH:mm:ss";
	public static final String FORMAT_TIME_HHMMSS = "HHmmss";

	/**
	 * deal with date string from Server
	 */
	public static final String TD_FORMAT = "(\\d{4})-(\\d{2})-(\\d{2})[Tt](\\d{2}):(\\d{2}):(\\d{2})([+-])((\\d{2}):(\\d{2}))";

	/**
	 * Returns DateFormat according to given pattern which is cached.<br/>
	 * This method is not public due to that the DateFormat instance may be
	 * altered by outside.
	 * 
	 * @param formatPattern
	 *            date format pattern string
	 * @return Cached DateFormat instance which should not be altered in any
	 *         way.
	 */
	protected static DateFormat getCachedDateFormat(String formatPattern) {

		DateFormat dateFormat = dateFormatMap.get(formatPattern);

		if (dateFormat == null) {

			dateFormat = new SimpleDateFormat(formatPattern,
					Locale.getDefault());
			dateFormatMap.put(formatPattern, dateFormat);

		}

		return dateFormat;

	}

	/**
	 * Returns DateFormat according to given pattern.
	 * 
	 * @param formatPattern
	 *            date format pattern string
	 * @return DateFormat instance.
	 */
	public static DateFormat getDateFormat(String formatPattern) {

		return new SimpleDateFormat(formatPattern, Locale.getDefault());

	}

	/**
	 * Format the date according to the pattern.
	 * 
	 * @param date
	 *            Date to format. If it's null, the result will be null.
	 * @param formatPattern
	 *            Date format pattern string. You can find common ones in
	 *            DateUtils class.
	 * @return Formatted date in String
	 * @see DateUtil
	 */
	public static String formatDate(Date date, String formatPattern) {
		if (date == null) {
			return null;
		}
		return getCachedDateFormat(formatPattern).format(date);
	}

	/**
	 * Format the date in default pattern: yyyy-MM-dd.
	 * 
	 * @param date
	 *            Date to format. If it's null, the result will be null.
	 * @return Formatted date in String
	 * @see DateUtil#FORMAT_DATE_DEFAULT
	 * @see DateUtil#formatDate(Date, String)
	 */
	public static String formatDate(Date date) {
		return formatDate(date, FORMAT_DATE_DEFAULT);
	}

	/**
	 * Format the date in default date-time pattern: yyyy-MM-dd HH:mm:ss
	 * 
	 * @param date
	 *            Date to format. If it's null, the result will be null.
	 * @return Formatted date-time in String
	 * @see DateUtil#FORMAT_DATETIME_DEFAULT
	 * @see DateUtil#formatDate(Date, String)
	 */
	public static String formatDateTime(Date date) {
		return formatDate(date, FORMAT_DATETIME_DEFAULT);
	}

	/**
	 * Format the date in default time pattern: HH:mm:ss
	 * 
	 * @param date
	 *            Date to format. If it's null, the result will be null.
	 * @return Formatted time in String
	 * @see DateUtil#FORMAT_TIME_DEFAULT
	 * @see DateUtil#formatDate(Date, String)
	 */
	public static String formatTime(Date date) {
		return formatDate(date, FORMAT_TIME_DEFAULT);
	}

	/**
	 * Returns current system date.
	 * 
	 * @return current system date.
	 * @see Date#Date()
	 */
	public static Date getCurrentDate() {
		return new Date();
	}

	/**
	 * Parse string value to Date by given format pattern. If parse failed, null
	 * would be returned.
	 * 
	 * @param stringValue
	 *            date value as string.
	 * @param formatPattern
	 *            format pattern.
	 * @return Date represents stringValue, null while parse exception occurred.
	 */
	public static Date parse(String stringValue, String formatPattern) {

		try {
			Date result = new Date();
			if (stringValue != null && stringValue.length() > 0) {

				result = getCachedDateFormat(formatPattern).parse(stringValue);

			} else {
				result = null;
			}

			return result;
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * Parses given Date to a Timestamp instance with same date in milliseconds
	 * precision.
	 * 
	 * @param date
	 *            Date to parse.
	 * @return Timestamp in milliseconds precision
	 */
	public static Timestamp parseTimestamp(Date date) {
		return new Timestamp(date.getTime());
	}

	/**
	 * Compares two dates based on the specified calendar field, and ignores
	 * those fields more trivial. Neither date could be null.<br/>
	 * For example, if calendarField is Calendar.Month, then result will be 0 if
	 * 2008-8-2 and 2008-8-10 is compared. But the result will be -1 if
	 * 
	 * @param date1
	 * @param date2
	 * @param calenderField
	 * @return date1 < date2 : <0<br/>
	 *         date1 = date2 : 0<br/>
	 *         date1 > date2 : >0
	 * @throws IllegalArgumentException
	 *             If either date is null.
	 * @see Calendar
	 */
	@SuppressWarnings("unused")
	public static int compareDateToField(final Date date1, final Date date2,
			int calendarField) {
		if (date1 == null || date2 == null) {
			throw new IllegalArgumentException("Neither date could be null");
		}
		Calendar instance = Calendar.getInstance();
		return 0;

	}
}
