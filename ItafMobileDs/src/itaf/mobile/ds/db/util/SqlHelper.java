package itaf.mobile.ds.db.util;

import itaf.mobile.core.utils.StringHelper;

public class SqlHelper {

	public static final String SQL_REPLACE_INTO = "REPLACE INTO ";
	public static final String SQL_INSERT_INTO = "INSERT INTO ";
	public static final String SQL_VALUES = " VALUES ";
	public static final String SQL_VALUES_MARK = "?";
	public static final String SQL_SELECT = "SELECT ";
	public static final String SQL_FROM = " FROM ";
	public static final String SQL_WHERE = " WHERE ";
	public static final String SQL_ORDER_BY = " ORDER BY ";

	public static String processReplaceSql(String tableName, String attrs) {
		StringBuilder sql = new StringBuilder();
		sql.append(SQL_REPLACE_INTO);
		sql.append(tableName);
		sql.append("(");
		sql.append(attrs);
		sql.append(")");
		sql.append(SQL_VALUES);
		sql.append("(");
		sql.append(processValues(attrs));
		sql.append(")");
		return sql.toString();
	}

	public static String processInsertSql(String tableName, String attrs) {
		StringBuilder sql = new StringBuilder();
		sql.append(SQL_INSERT_INTO);
		sql.append(tableName);
		sql.append("(");
		sql.append(attrs);
		sql.append(")");
		sql.append(SQL_VALUES);
		sql.append("(");
		sql.append(processValues(attrs));
		sql.append(")");
		return sql.toString();
	}

	public static String processSelectSql(String selectBody, String fromBody) {
		return processSelectSql(selectBody, fromBody, "", "");
	}

	public static String processSelectSql(String selectBody, String fromBody,
			String whereBody) {
		return processSelectSql(selectBody, fromBody, whereBody, "");
	}

	public static String processSelectSql(String selectBody, String fromBody,
			String whereBody, String orderBody) {
		StringBuilder sql = new StringBuilder();
		sql.append(SQL_SELECT);
		sql.append(selectBody);
		sql.append(SQL_FROM);
		sql.append(fromBody);
		if (StringHelper.isNotEmpty(whereBody)) {
			sql.append(SQL_WHERE);
			sql.append(whereBody);
		}
		if (StringHelper.isNotEmpty(orderBody)) {
			sql.append(SQL_ORDER_BY);
			sql.append(orderBody);
		}
		return sql.toString();
	}

	private static String processValues(String attrs) {
		int leg = attrs.split(",").length;
		String result = SQL_VALUES_MARK;
		for (int i = 1; i < leg; i++) {
			result += ", " + SQL_VALUES_MARK;
		}
		return result;
	}

}
