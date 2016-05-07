package itaf.mobile.core.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 验证帮助类
 * 
 * 
 * @author
 * 
 * @updateDate 2014年2月27日
 */
public class ValidateHelper {

	/**
	 * 是否为手机号码
	 * 
	 * @param target
	 *            验证对象
	 * @return 是 true 否 false
	 */
	public static boolean isMobileNo(String target) {
		Pattern p = Pattern
				.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
		Matcher m = p.matcher(target);
		return m.matches();
	}

	/**
	 * 是否为座机电话号码
	 * 
	 * @param target
	 *            验证对象
	 * @return 是 true 否 false
	 */
	public static boolean isLandlineNo(String target) {
		Pattern p = Pattern
				.compile("^([0-9]{3,4}-?[0-9]{8})|([0-9]{4}-?[0-9]{7})$");
		Matcher m = p.matcher(target);
		return m.matches();
	}

	/**
	 * 是否为电话号码（手机号码或者座机电话号码）
	 * 
	 * @param target
	 *            验证对象
	 * @return 是 true 否 false
	 */
	public static boolean isTelephoneNo(String target) {
		if (isLandlineNo(target)) {
			return true;
		}
		return isMobileNo(target);
	}

	/**
	 * 是否为正确的邮件格式
	 * 
	 * @param target
	 *            验证对象
	 * @return 是 true 否 false
	 */
	public static boolean isEmail(String target) {
		return isEmpty(target) ? false : target
				.matches("^[\\w-]+(\\.[\\w-]+)*@[\\w-]+(\\.[\\w-]+)+$");
	}

	/**
	 * 是否为空的字符
	 * 
	 * @param target
	 *            验证对象
	 * @return 是 true 否 false
	 */
	private static boolean isEmpty(String target) {
		return target == null || target.trim().length() == 0;
	}

}
