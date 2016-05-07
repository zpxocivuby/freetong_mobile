package itaf.mobile.app.ui.adapter;

import itaf.mobile.core.utils.DateUtil;

import java.util.Date;

import android.widget.BaseAdapter;

/**
 * AbstractBaseAdapter 抽象出一些公共的方法供之类使用并能继承BaseAdapter
 * 
 * @author
 * 
 * @update 2013年10月15日
 */
public abstract class AbstractBaseAdapter extends BaseAdapter {

	public static String processDateToHightLightTime(Date date) {
		if (date == null) {
			return "";
		}
		String targetDate = DateUtil.formatDate(date,
				DateUtil.FORMAT_DATE_YYYY_MM_DD);
		String now = DateUtil.formatDate(new Date(),
				DateUtil.FORMAT_DATE_YYYY_MM_DD);
		if (now.equals(targetDate)) {
			return DateUtil.formatDate(date, DateUtil.FORMAT_TIME_HH_MM_SS);
		}
		return targetDate;
	}

	protected String processDate(Date target) {
		return processDate(target, DateUtil.FORMAT_DATE_DEFAULT);
	}

	protected String processDate(Date target, String patten) {
		if (target == null) {
			return "";
		}
		return DateUtil.formatDate(target, patten);
	}

	protected boolean processBoolean(Boolean target) {
		return target == null ? false : target;
	}
}
