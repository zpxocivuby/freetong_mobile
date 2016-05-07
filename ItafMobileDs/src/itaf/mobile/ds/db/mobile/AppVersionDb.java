package itaf.mobile.ds.db.mobile;

import itaf.mobile.ds.db.base.BaseTempleteDaoImpl;
import itaf.mobile.ds.domain.AppVersion;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

/**
 * 系统内部版本
 * 
 * 
 * @author
 * 
 * @updateDate 2014年3月17日
 */
public class AppVersionDb extends BaseTempleteDaoImpl<AppVersion> {

	public AppVersionDb(Context context) {
		super(context);
	}

	public void saveOrUpdate(AppVersion appVersion) {
		String sql = "REPLACE  INTO [APP_VERSION] ([TYPE],[STATUS]) VALUES(?,?)";
		Object[] params = new Object[] { appVersion.getType(),
				appVersion.getStatus() };
		this.execSql(sql, params);
	}

	@SuppressWarnings("unchecked")
	public List<AppVersion> findAllByType(String type) {
		String sql = "SELECT [TYPE],[STATUS] FROM [APP_VERSION] WHERE [TYPE] =? ORDER BY [STATUS] DESC";
		String[] sqlParams = new String[] { type };
		Object obj = this.query(sql, sqlParams, 1);
		return obj == null ? null : (List<AppVersion>) obj;
	}

	public AppVersion findFirstLogin(String type) {
		String sql = "SELECT [TYPE],[STATUS] FROM [APP_VERSION] WHERE [TYPE] =?";
		String[] sqlParams = new String[] { type };
		Object obj = this.query(sql, sqlParams, 2);
		return obj == null ? null : (AppVersion) obj;
	}

	/**
	 * 更新的时候更新表结构
	 */
	public void alterTable(int dbVersion) {
		try {
			if (dbVersion <= 1) {
				this.execSql("ALTER TABLE [TS_USER] ADD COLUMN [ORDER_CURSOR] INTEGER DEFAULT 0");
			}
			if (dbVersion <= 2) {
				this.execSql("ALTER TABLE [CALENDAR_EVENTS] ADD COLUMN [CURR_USERNAME] VARCHAR(30)");
				this.execSql("ALTER TABLE [CALENDAR_EVENTS_REPEAT] ADD COLUMN [CURR_USERNAME] VARCHAR(30)");
				this.execSql("ALTER TABLE [CALENDAR_INVITATION_USER] ADD COLUMN [CURR_USERNAME] VARCHAR(30)");
			}
		} catch (Exception e) {
			Log.d(this.getClass().getName(), "执行表结构变更出错！" + e.getMessage());
		}

	}

	@Override
	public Object processCursor(Cursor cursor, int cursorKey) {
		Object result = null;
		switch (cursorKey) {
		case 1:
			List<AppVersion> versions = new ArrayList<AppVersion>();
			while (cursor.moveToNext()) {
				AppVersion version = new AppVersion();
				setAppVersion(cursor, version);
				versions.add(version);
			}
			result = versions;
			break;
		case 2:
			AppVersion appVersion = null;
			if (cursor.moveToNext()) {
				appVersion = new AppVersion();
				setAppVersion(cursor, appVersion);
			}
			result = appVersion;
			break;
		}
		return result;
	}

	private void setAppVersion(Cursor cursor, AppVersion appVersion) {
		appVersion.setStatus(cursor.getString(cursor.getColumnIndex("STATUS")));
		appVersion.setType(cursor.getString(cursor.getColumnIndex("TYPE")));
	}

}
