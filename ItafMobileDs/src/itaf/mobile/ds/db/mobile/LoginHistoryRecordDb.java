package itaf.mobile.ds.db.mobile;

import itaf.mobile.core.utils.DateUtil;
import itaf.mobile.ds.db.base.BaseTempleteDaoImpl;
import itaf.mobile.ds.db.util.SqlHelper;
import itaf.mobile.ds.domain.LoginHistoryRecord;

import java.util.Date;

import android.content.Context;
import android.database.Cursor;

/**
 * 登陆历史记录数据库操作类
 *
 *
 * @author
 *
 * @UpdateDate 2014年9月10日
 */
public class LoginHistoryRecordDb extends
		BaseTempleteDaoImpl<LoginHistoryRecord> {

	private static final String TABLE_NAME = " [LOGIN_HISTORY_RECORD] ";
	private static final String ATTRS = "[ID],[USERNAME],[PASSWORD],[MOBILE],[REMEMBER_PASSWORD],[AUTO_LOGIN],[LOGIN_TIME]";

	public LoginHistoryRecordDb(Context context) {
		super(context);
	}

	// 保存或者更新用户信息
	public void saveOrUpdate(LoginHistoryRecord target) {
		Object[] params = new Object[] {
				target.getId(),
				target.getUsername(),
				target.getPassword(),
				target.getMobile(),
				target.getRememberPassword(),
				target.getAutoLogin(),
				DateUtil.formatDate(new Date(),
						DateUtil.FORMAT_DATETIME_DEFAULT) };
		this.execSql(SqlHelper.processReplaceSql(TABLE_NAME, ATTRS), params);
	}

	// 查找最后一次登陆的那个用户记录
	public LoginHistoryRecord findLatestLoginRecord() {
		Object obj = this.query(SqlHelper.processSelectSql(ATTRS, TABLE_NAME,
				"", "[LOGIN_TIME] DESC"), 1);
		return obj == null ? null : (LoginHistoryRecord) obj;
	}

	// 取消自动登录或者勾选自动登录
	public void updateAutoLogin(String username, Long auto) {
		String sql = "UPDATE [LOGIN_HISTORY_RECORD] SET [AUTO_LOGIN]=? WHERE [USERNAME] = ?";
		Object[] params = new Object[] { auto, username };
		this.execSql(sql, params);
	}

	@Override
	public Object processCursor(Cursor cursor, int cursorKey) {
		Object result = null;
		switch (cursorKey) {
		case 1:
			if (cursor.moveToNext()) {
				LoginHistoryRecord dto = new LoginHistoryRecord();
				dto.setId(getLongFromCursor(cursor, "ID"));
				dto.setUsername(getStringFromCursor(cursor, "USERNAME"));
				dto.setPassword(getStringFromCursor(cursor, "PASSWORD"));
				dto.setMobile(getStringFromCursor(cursor, "MOBILE"));
				dto.setRememberPassword(getIntegerFromCursor(cursor,
						"REMEMBER_PASSWORD"));
				dto.setAutoLogin(getIntegerFromCursor(cursor, "AUTO_LOGIN"));
				dto.setLoginTime(getDateFromCursor(cursor, "LOGIN_TIME",
						DateUtil.FORMAT_DATETIME_DEFAULT));
				result = dto;
			}
			break;
		}
		return result;
	}

}
