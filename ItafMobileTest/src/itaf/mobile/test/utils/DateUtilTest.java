package itaf.mobile.test.utils;

import itaf.mobile.core.utils.DateUtil;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;
import android.annotation.SuppressLint;

public class DateUtilTest extends TestCase {
	protected static Map<String, DateFormat> dateFormatMap = new HashMap<String, DateFormat>();

	protected void setUp() throws Exception {
		super.setUp();
	}

	public void testParse() {

		Date dddf = DateUtil.parse("2013-05-28T00:00:00+08:00",
				DateUtil.FORMAT_DATETIME_YYYYMMDDTHHMMSSZ);
		System.out.println(DateUtil.formatDate(dddf,
				DateUtil.FORMAT_DATETIME_YYYYMMDDTHHMMSSZ));
		System.out.println(dddf);
	}

	public void testParseTimestamp() {

	}

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
	 * Returns DateFormat according to given pattern which is cached.<br/>
	 * This method is not public due to that the DateFormat instance may be
	 * altered by outside.
	 * 
	 * @param formatPattern
	 *            date format pattern string
	 * @return Cached DateFormat instance which should not be altered in any
	 *         way.
	 */
	@SuppressLint("SimpleDateFormat")
	protected static DateFormat getCachedDateFormat(String formatPattern) {

		DateFormat dateFormat = dateFormatMap.get(formatPattern);

		if (dateFormat == null) {
			dateFormat = new SimpleDateFormat(formatPattern);
			dateFormatMap.put(formatPattern, dateFormat);

		}

		return dateFormat;

	}
}
