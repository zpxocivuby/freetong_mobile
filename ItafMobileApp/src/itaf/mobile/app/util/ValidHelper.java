package itaf.mobile.app.util;

import itaf.mobile.core.utils.StringHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.net.ParseException;

public class ValidHelper {

	public static final boolean notEmpty(Activity mActivity, String target,
			String msg) {
		if (StringHelper.isNotEmpty(target)) {
			return true;
		}
		ToastHelper.showToast(mActivity, msg + "不能为空！");
		return false;
	}

	public static final boolean mustEquals(Activity mActivity, String target,
			String confirmTarget, String msg) {
		if (target.equals(confirmTarget)) {
			return true;
		}
		ToastHelper.showToast(mActivity, msg + "不一致！");
		return false;
	}

	public static boolean isPhone(Activity mActivity, String phone) {
		Pattern phonePattern = Pattern.compile("^1\\d{10}$");
		Matcher matcher = phonePattern.matcher(phone);
		if (matcher.find()) {
			return true;
		}
		ToastHelper.showToast(mActivity, "手机号码不正确！");
		return false;
	}

	@SuppressLint("SimpleDateFormat")
	public static boolean isIdCard(Activity mActivity, String target)
			throws ParseException {
		String errorInfo = "";// 记录错误信息
		String[] ValCodeArr = { "1", "0", "x", "9", "8", "7", "6", "5", "4",
				"3", "2" };
		String[] Wi = { "7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7",
				"9", "10", "5", "8", "4", "2" };
		String Ai = "";
		// ================ 号码的长度 15位或18位 ================
		if (target.length() != 15 && target.length() != 18) {
			errorInfo = "身份证号码长度应该为15位或18位！";
			ToastHelper.showToast(mActivity, errorInfo);
			return false;
		}
		// =======================(end)========================

		// ================ 数字 除最后以为都为数字 ================
		if (target.length() == 18) {
			Ai = target.substring(0, 17);
		} else if (target.length() == 15) {
			Ai = target.substring(0, 6) + "19" + target.substring(6, 15);
		}
		if (isNumeric(Ai) == false) {
			errorInfo = "身份证15位号码都应为数字 ; 18位号码除最后一位外，都应为数字！";
			ToastHelper.showToast(mActivity, errorInfo);
			return false;
		}
		// =======================(end)========================

		// ================ 出生年月是否有效 ================
		String strYear = Ai.substring(6, 10);// 年份
		String strMonth = Ai.substring(10, 12);// 月份
		String strDay = Ai.substring(12, 14);// 月份
		if (isDataFormat(strYear + "-" + strMonth + "-" + strDay) == false) {
			errorInfo = "身份证生日无效！";
			ToastHelper.showToast(mActivity, errorInfo);
			return false;
		}
		GregorianCalendar gc = new GregorianCalendar();
		SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
		try {
			if ((gc.get(Calendar.YEAR) - Integer.parseInt(strYear)) > 150
					|| (gc.getTime().getTime() - s.parse(
							strYear + "-" + strMonth + "-" + strDay).getTime()) < 0) {
				errorInfo = "身份证生日不在有效范围！";
				ToastHelper.showToast(mActivity, errorInfo);
				return false;
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}
		if (Integer.parseInt(strMonth) > 12 || Integer.parseInt(strMonth) == 0) {
			errorInfo = "身份证月份无效！";
			ToastHelper.showToast(mActivity, errorInfo);
			return false;
		}
		if (Integer.parseInt(strDay) > 31 || Integer.parseInt(strDay) == 0) {
			errorInfo = "身份证日期无效！";
			ToastHelper.showToast(mActivity, errorInfo);
			return false;
		}
		// =====================(end)=====================

		// ================ 地区码时候有效 ================
		HashMap<String, String> h = getAreaCode();
		if (h.get(Ai.substring(0, 2)) == null) {
			errorInfo = "身份证地区编码错误！";
			ToastHelper.showToast(mActivity, errorInfo);
			return false;
		}
		// ==============================================

		// ================ 判断最后一位的值 ================
		int TotalmulAiWi = 0;
		for (int i = 0; i < 17; i++) {
			TotalmulAiWi = TotalmulAiWi
					+ Integer.parseInt(String.valueOf(Ai.charAt(i)))
					* Integer.parseInt(Wi[i]);
		}
		int modValue = TotalmulAiWi % 11;
		String strVerifyCode = ValCodeArr[modValue];
		Ai = Ai + strVerifyCode;

		if (target.length() == 18) {
			if (Ai.equals(target) == false) {
				errorInfo = "身份证无效，不是合法的身份证号码！";
			}
		}
		// =====================(end)=====================
		if (errorInfo.length() > 0) {
			ToastHelper.showToast(mActivity, errorInfo);
			return false;
		}

		return true;
	}

	/**
	 * 功能：判断字符串是否为数字
	 * 
	 * @param str
	 * @return
	 */
	private static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (isNum.matches()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 功能：设置地区编码
	 * 
	 * @return HashMap 对象
	 */
	private static HashMap<String, String> getAreaCode() {
		HashMap<String, String> result = new HashMap<String, String>();
		result.put("11", "北京");
		result.put("12", "天津");
		result.put("13", "河北");
		result.put("14", "山西");
		result.put("15", "内蒙古");
		result.put("21", "辽宁");
		result.put("22", "吉林");
		result.put("23", "黑龙江");
		result.put("31", "上海");
		result.put("32", "江苏");
		result.put("33", "浙江");
		result.put("34", "安徽");
		result.put("35", "福建");
		result.put("36", "江西");
		result.put("37", "山东");
		result.put("41", "河南");
		result.put("42", "湖北");
		result.put("43", "湖南");
		result.put("44", "广东");
		result.put("45", "广西");
		result.put("46", "海南");
		result.put("50", "重庆");
		result.put("51", "四川");
		result.put("52", "贵州");
		result.put("53", "云南");
		result.put("54", "西藏");
		result.put("61", "陕西");
		result.put("62", "甘肃");
		result.put("63", "青海");
		result.put("64", "宁夏");
		result.put("65", "新疆");
		result.put("71", "台湾");
		result.put("81", "香港");
		result.put("82", "澳门");
		result.put("91", "国外");
		return result;
	}

	/**
	 * 验证日期字符串是否是YYYY-MM-DD格式
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isDataFormat(String str) {
		boolean flag = false;
		// String
		// regxStr="[1-9][0-9]{3}-[0-1][0-2]-((0[1-9])|([12][0-9])|(3[01]))";
		String regxStr = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$";
		Pattern pattern1 = Pattern.compile(regxStr);
		Matcher isNo = pattern1.matcher(str);
		if (isNo.matches()) {
			flag = true;
		}
		return flag;
	}

}
