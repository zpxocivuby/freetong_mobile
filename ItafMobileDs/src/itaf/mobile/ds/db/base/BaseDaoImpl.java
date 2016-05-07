package itaf.mobile.ds.db.base;

import itaf.mobile.core.constant.AppConstants;
import itaf.mobile.core.utils.DateUtil;

import java.util.Date;

import android.content.Context;
import android.database.Cursor;

/**
 * 基础数据库操作
 * 
 * @author XININ
 * 
 * @updateDate 2013-6-26
 */
public abstract class BaseDaoImpl {

	private static ThreadLocal<DbOpenHelper> threadLocalDBOpenHelper = new ThreadLocal<DbOpenHelper>();

	private Context dbcontext;

	public DbOpenHelper getDbOpenHelper() {
		Object object = threadLocalDBOpenHelper.get();
		if (object == null) {
			threadLocalDBOpenHelper.set(new DbOpenHelper(dbcontext));
		}
		return threadLocalDBOpenHelper.get();
	}

	public BaseDaoImpl(Context context) {
		dbcontext = context;
		threadLocalDBOpenHelper.set(new DbOpenHelper(dbcontext));

	}

	protected Long getLongFromCursor(Cursor cursor, String target) {
		try {
			return Long.valueOf(getStringFromCursor(cursor, target));
		} catch (Exception e) {

		}
		return null;
	}

	protected Integer getIntegerFromCursor(Cursor cursor, String target) {
		try {
			return Integer.valueOf(getStringFromCursor(cursor, target));
		} catch (Exception e) {

		}
		return null;
	}

	protected Boolean getBooleanFromCursor(Cursor cursor, String target) {
		String result = getStringFromCursor(cursor, target);
		return "1".equals(result) || "true".equals(result)
				|| AppConstants.SET_ON.equals(result);
	}

	protected String getStringFromCursor(Cursor cursor, String target) {
		String result = cursor.getString(cursor.getColumnIndex(target));
		if (result == null || result.length() <= 0 || "null".equals(result)) {
			return null;
		}
		return result;
	}

	protected Date getDateFromCursor(Cursor cursor, String target) {
		return getDateFromCursor(cursor, target, DateUtil.FORMAT_DATE_DEFAULT);
	}

	protected Date getDateFromCursor(Cursor cursor, String target, String patten) {
		return DateUtil.parse(getStringFromCursor(cursor, target), patten);
	}

}
