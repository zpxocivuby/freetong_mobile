package itaf.mobile.ds.db.base;

import itaf.framework.base.dto.WsPageResult;
import itaf.mobile.core.exception.AppException;

import java.util.Arrays;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * 执行模版
 * 
 * 
 * @author
 * 
 * @update 2013年11月29日
 */
public abstract class BaseTempleteDaoImpl<T> extends BaseDaoImpl {

	public static final String SQL_LIMIT_OFFSET = " LIMIT ? OFFSET ? ";

	public BaseTempleteDaoImpl(Context context) {
		super(context);
	}

	/**
	 * 
	 * @param sql
	 * @param sqlParams
	 * @return
	 */
	public Object query(String sql, int cursorKey) {
		Log.d(this.getClass().getName(), "sql=" + sql);
		SQLiteDatabase db = getDbOpenHelper().getReadableDatabase();
		Object result = null;
		Cursor cursor = null;
		try {
			cursor = db.rawQuery(sql, new String[] {});
			result = processCursor(cursor, cursorKey);
		} catch (Exception e) {
			e.printStackTrace();
			throw new AppException(e);
		} finally {
			try {
				if (cursor != null) {
					cursor.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new AppException(e);
			} finally {
				if (db != null) {
					db.close();
				}
			}
		}
		return result;
	}

	/**
	 * 
	 * @param sql
	 * @param sqlParams
	 * @return
	 */
	public Object query(String sql, String[] sqlParams, int cursorKey) {
		Log.d(this.getClass().getName(),
				"sql=" + sql + "@sqlParams=" + Arrays.toString(sqlParams));
		SQLiteDatabase db = getDbOpenHelper().getReadableDatabase();
		Object result = null;
		Cursor cursor = null;
		try {
			cursor = db.rawQuery(sql, sqlParams);
			result = processCursor(cursor, cursorKey);
		} catch (Exception e) {
			e.printStackTrace();
			throw new AppException(e);
		} finally {
			try {
				if (cursor != null) {
					cursor.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new AppException(e);
			} finally {
				if (db != null) {
					db.close();
				}
			}
		}
		return result;
	}

	public int queryCount(String sql, String[] sqlParams) {
		Log.d(this.getClass().getName(),
				"sql=" + sql + "@sqlParams=" + Arrays.toString(sqlParams));
		SQLiteDatabase db = getDbOpenHelper().getReadableDatabase();
		Cursor cursor = null;
		int result = 0;
		try {
			cursor = db.rawQuery(sql, sqlParams);
			if (cursor.moveToNext()) {
				result = cursor.getInt(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new AppException(e);
		} finally {
			try {
				if (cursor != null) {
					cursor.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new AppException(e);
			} finally {
				if (db != null) {
					db.close();
				}
			}
		}
		return result;
	}

	private int getTotalCount(SQLiteDatabase db, String type, String username) {
		Cursor cursor = null;
		int result = 0;
		try {
			cursor = db
					.rawQuery(
							"SELECT [VALUE] FROM [TOTAL_COUNT_DTO] WHERE [TYPE] = ? AND [CURR_USERNAME] = ?",
							new String[] { type, username });
			if (cursor.moveToNext()) {
				result = cursor.getInt(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new AppException(e);
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public WsPageResult<T> queryPager(String sql, String[] sqlParams,
			int cursorKey, String type, String userCode) {
		Log.d(this.getClass().getName(),
				"sql=" + sql + "@sqlParams=" + Arrays.toString(sqlParams));
		SQLiteDatabase db = getDbOpenHelper().getReadableDatabase();
		Cursor cursor = null;
		WsPageResult<T> result = new WsPageResult<T>();
		try {
			cursor = db.rawQuery(sql, sqlParams);
			result.setContent((List<T>) processCursor(cursor, cursorKey));
			result.setTotalCount((getTotalCount(db, type, userCode)));
		} catch (Exception e) {
			e.printStackTrace();
			throw new AppException(e);
		} finally {
			try {
				if (cursor != null) {
					cursor.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new AppException(e);
			} finally {
				if (db != null) {
					db.close();
				}
			}
		}
		return result;
	}

	public void execSql(String sql) {
		Log.d(this.getClass().getName(), "sql=" + sql);
		SQLiteDatabase db = getDbOpenHelper().getWritableDatabase();
		try {
			db.execSQL(sql);
		} catch (Exception e) {
			e.printStackTrace();
			throw new AppException(e);
		} finally {
			if (db != null) {
				db.close();
			}
		}
	}

	public void execSql(String sql, Object[] sqlParams) {
		Log.d(this.getClass().getName(),
				"sql=" + sql + "@sqlParams=" + Arrays.toString(sqlParams));
		SQLiteDatabase db = getDbOpenHelper().getWritableDatabase();
		try {
			db.execSQL(sql, sqlParams);
		} catch (Exception e) {
			e.printStackTrace();
			throw new AppException(e);
		} finally {
			if (db != null) {
				db.close();
			}
		}
	}

	public void execSqls(List<String> sqls, List<Object[]> sqlParams) {
		SQLiteDatabase db = getDbOpenHelper().getWritableDatabase();
		try {
			for (int i = 0; i < sqls.size(); i++) {
				db.execSQL(sqls.get(i), sqlParams.get(i));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new AppException(e);
		} finally {
			if (db != null) {
				db.close();
			}
		}
	}

	public abstract Object processCursor(Cursor cursor, int cursorKey);

}
