package itaf.mobile.ds.db.mobile;

import itaf.mobile.ds.db.base.BaseDaoImpl;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * 清理缓存：分页的数据
 * 
 * @author
 * 
 * @update 2013年10月15日
 */
public class ClearCacheDb extends BaseDaoImpl {

	public ClearCacheDb(Context context) {
		super(context);
	}

	public void clearAllCache() {
		SQLiteDatabase db = getDbOpenHelper().getWritableDatabase();
		try {
			db.execSQL("DELETE FROM [LOGIN_HISTORY_RECORD]");
			db.execSQL("DELETE FROM [TOTAL_COUNT_DTO]");
		} catch (Exception e) {
			Log.e(this.getClass().getName(), "清理缓存异常：" + e.getMessage());
		} finally {
			if (db != null) {
				try {
					db.close();
				} catch (Exception e) {
					Log.e(this.getClass().getName(),
							"清理缓存db关闭异常：" + e.getMessage());
				}
			}
		}
	}

	public void clearCacheByCurrUsername(String username) {
		SQLiteDatabase db = getDbOpenHelper().getWritableDatabase();
		Object[] param = new Object[] { username };
		try {
			db.execSQL(
					"DELETE FROM [LOGIN_HISTORY_RECORD] WHERE [CURR_USERNAME] = ?",
					param);
			db.execSQL(
					"DELETE FROM [TOTAL_COUNT_DTO] WHERE [CURR_USERNAME] = ?",
					param);
		} catch (Exception e) {
			Log.e(this.getClass().getName(), "清理缓存异常：" + e.getMessage());
		} finally {
			if (db != null) {
				try {
					db.close();
				} catch (Exception e) {
					Log.e(this.getClass().getName(),
							"清理缓存db关闭异常：" + e.getMessage());
				}
			}
		}
	}
}
