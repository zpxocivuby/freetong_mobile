package itaf.mobile.core.utils;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;

/**
 * String帮助类
 * 
 * 
 * @author
 * 
 * @update 2013年11月19日
 */
public class StringHelper {

	public static final String RMB = "￥";
	public static final String KM = "Km";

	/**
	 * 集合转换成String
	 * 
	 * @param target
	 *            Collection<Object>
	 * @return 使用","分割
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static String collectionToString(Collection target) {
		String result = "";
		if (target == null || target.size() == 0) {
			return result;
		}
		for (Iterator<Object> iterator = target.iterator(); iterator.hasNext();) {
			Object object = (Object) iterator.next();
			if (object != null) {
				result += object.toString() + ",";
			}
		}
		return result.substring(0, result.length() - 1);

	}

	/**
	 * 第一个字母大写
	 * 
	 * @param target
	 *            String
	 * @return String
	 */
	public static String capitalize(String target) {
		if (isEmpty(target)) {
			return target;
		}
		return target.substring(0, 1).toUpperCase(Locale.getDefault())
				+ target.substring(1);
	}

	/**
	 * 第一个字母小写
	 * 
	 * @param target
	 *            String
	 * @return String
	 */
	public static String uncapitalize(String target) {
		if (isEmpty(target)) {
			return target;
		}
		return target.substring(0, 1).toLowerCase(Locale.getDefault())
				+ target.substring(1);
	}

	/**
	 * 转换成小写
	 * 
	 * @param target
	 *            String
	 * @return String
	 */
	public static String toLowerCase(String target) {
		return isNotEmpty(target) ? target.toLowerCase(Locale.getDefault())
				: "";
	}

	/**
	 * 转换成大写
	 * 
	 * @param target
	 *            String
	 * @return String
	 */
	public static String toUpperCase(String target) {
		return isNotEmpty(target) ? target.toUpperCase(Locale.getDefault())
				: "";
	}

	/**
	 * null转为""并去空格
	 * 
	 * @param target
	 *            String
	 * @return String
	 */
	public static String trimToEmpty(String target) {
		return target == null ? "" : target.trim();
	}

	/**
	 * long转为String
	 * 
	 * @param target
	 *            String
	 * @return String
	 */
	public static String longToString(Long target) {
		return target == null ? "0" : target.toString();
	}

	// /**
	// * long转为String
	// *
	// * @param target
	// * String
	// * @return String
	// */
	// public static String floatToRating(Float target) {
	// if (target == null) {
	// return "0.0";
	// }
	// String[] tempArr = target.toString().split("\\.");
	// String result = "5";
	// if (Integer.valueOf(tempArr[1].substring(0, 1)) < 5) {
	// result = "0";
	// }
	// return tempArr[0] + "." + result;
	// }

	public static void main(String[] args) {

	}

	/**
	 * long转为String
	 * 
	 * @param target
	 *            String
	 * @return String
	 */
	public static String longToKm(Long target) {
		return target == null ? "0" + KM : target.toString() + KM;
	}

	/**
	 * BigDecimal转为String
	 * 
	 * @param target
	 *            String
	 * @return String
	 */
	public static String bigDecimalToRmb(BigDecimal target) {
		return target == null ? RMB + "0.0" : RMB + target.toString();
	}

	/**
	 * 是否为空
	 * 
	 * @param target
	 *            String
	 * @return 空:true 非空:false
	 */
	public static boolean isEmpty(String target) {
		return target == null || target.trim().length() == 0;
	}

	/**
	 * 是否为非空
	 * 
	 * @param target
	 *            String
	 * @return 非空:true 空:false
	 */
	public static boolean isNotEmpty(String target) {
		return target != null && target.trim().length() > 0;
	}

	/**
	 * 
	 * 根据前缀和后缀截取中间的字符串
	 * 
	 * @param target
	 *            String
	 * @param prefix
	 *            前缀
	 * @param suffix
	 *            后缀
	 * @return String
	 */
	public static String substringBetween(String target, String prefix,
			String suffix) {
		if (target == null) {
			return target;
		}
		int index = target.indexOf(prefix);
		if (index < 0) {
			return null;
		}
		int index1 = target.indexOf(suffix, index + prefix.length());
		if (index1 < 0) {
			return null;
		}
		return target.substring(index + prefix.length(), index1);
	}
}
