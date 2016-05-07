package itaf.mobile.ds.db.base;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 创建数据库和数据库版本控制
 *
 *
 * @author
 *
 * @UpdateDate 2014年9月10日
 */
public class DbOpenHelper extends SQLiteOpenHelper {
	// 数据库名称，如果是null就会创建一个在内存中的数据库，当然内存被清理数据也就消失了
	private static final String DATABASE_NAME = "db_mobile_side.db";
	// 数据库版本，必须大于1，不然抛异常
	private static final int DATABASE_VERSION = 1;

	public DbOpenHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// 登录历史记录
	private String CREATE_LOGIN_HISTORY_RECORD = "CREATE TABLE [LOGIN_HISTORY_RECORD] ("
			+ "[ID] LONG PRIMARY KEY"
			+ " ,[USERNAME] VARCHAR(300)"
			+ " ,[PASSWORD] VARCHAR(100)"
			+ " ,[MOBILE] VARCHAR(20)"
			+ " ,[REMEMBER_PASSWORD] INTEGER"
			+ " ,[AUTO_LOGIN] INTEGER"
			+ " ,[LOGIN_TIME] DATETIME" + ");";

	// 商品品类
	private String CREATE_PRODUCT_CATEGORY_DTO = "CREATE TABLE [PRODUCT_CATEGORY_DTO] ("
			+ "[ID] LONG PRIMARY KEY"
			+ " ,[CATEGORY_NAME] VARCHAR(300)"
			+ " ,[CATEGORY_CODE] VARCHAR(300)"
			+ " ,[PERENT_ID] LONG"
			+ " ,[IS_LEAF] LONG"
			+ " ,[ORDER_NO] LONG"
			+ " ,[MARK_FOR_DELETE] INTEGER"
			+ " ,[CREATED_BY] LONG"
			+ " ,[CREATED_DATE] DATETIME"
			+ " ,[UPDATED_BY] LONG"
			+ " ,[UPDATED_DATE] DATETIME" + ");";

	// 商家服务类型
	private String CREATE_SERVICE_PROVIDER_TYPE_DTO = "CREATE TABLE [SERVICE_PROVIDER_TYPE_DTO] ("
			+ "[ID] LONG PRIMARY KEY"
			+ " ,[TYPE_NAME] VARCHAR(300)"
			+ " ,[TYPE_CODE] VARCHAR(300)"
			+ " ,[PERENT_ID] LONG"
			+ " ,[IS_LEAF] LONG"
			+ " ,[ORDER_NO] LONG"
			+ " ,[MARK_FOR_DELETE] INTEGER"
			+ " ,[CREATED_BY] LONG"
			+ " ,[CREATED_DATE] DATETIME"
			+ " ,[UPDATED_BY] LONG"
			+ " ,[UPDATED_DATE] DATETIME" + ");";

	// 版本控制
	private String CREATE_APP_VERSION = "CREATE TABLE [APP_VERSION] ("
			+ " [TYPE] VARCHAR(30)" + " ,[STATUS] VARCHAR(10)"
			+ ", constraint PK_APP_VERSION PRIMARY KEY ([TYPE],[STATUS]));";

	// totalCount表
	private String CREATE_TOTAL_COUNT = "CREATE TABLE [TOTAL_COUNT_DTO] ("
			+ "[TYPE] VARCHAR(300) "
			+ " ,[VALUE] INTEGER"
			+ " ,[CURR_USERNAME] VARCHAR(30)"
			+ ", constraint PK_TOTAL_COUNT PRIMARY KEY ([TYPE],[CURR_USERNAME]));";

	// 设置通用
	private String CREATE_SETTING_COMMON = "CREATE TABLE [USER_COMMON_SETTING] ("
			+ " [ID] LONG PRIMARY KEY"
			+ " ,[TYPE] VARCHAR(100)"
			+ " ,[VALUE] VARCHAR(100)" + " ,[CURR_USERNAME] VARCHAR(30)" + ");";

	// IM最新聊天对象
	private String CREATE_IM_LASTEST_CHAT_OBJECT = "CREATE TABLE [IM_LASTEST_CHAT_OBJECT] ("
			+ " [JID] VARCHAR(300)"
			+ " ,[HEAD_URL] VARCHAR(100)"
			+ " ,[NICKNAME] VARCHAR(300)"
			+ " ,[STATUS] VARCHAR(30)"
			+ " ,[LASTEST_CHAT_CONTENT] VARCHAR(3000)"
			+ " ,[LASTEST_CHAT_DATETIME] DATETIME"
			+ " ,[TYPE] INTEGER"
			+ " ,[CURR_USERNAME] VARCHAR(30)"
			+ " , constraint PK_IM_LASTEST_CHAT_OBJECT PRIMARY KEY ([JID],[CURR_USERNAME]));";

	// IM历史聊天记录
	private String CREATE_IM_HISTORY_CHAT_RECORD = "CREATE TABLE [IM_HISTORY_CHAT_RECORD] ("
			+ " [HISTORY_CHAT_RECORD_ID] LONG PRIMARY KEY"
			+ " ,[JID] VARCHAR(300)"
			+ " ,[HEAD_URL] VARCHAR(100)"
			+ " ,[NICKNAME] VARCHAR(300)"
			+ " ,[CHAT_CONTENT] VARCHAR(3000)"
			+ " ,[CHAT_TIME] DATETIME"
			+ " ,[CHAT_TYPE] VARCHAR(10)"
			+ " ,[CURR_USERNAME] VARCHAR(30)" + ");";

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_LOGIN_HISTORY_RECORD);
		db.execSQL(CREATE_PRODUCT_CATEGORY_DTO);
		db.execSQL(CREATE_SERVICE_PROVIDER_TYPE_DTO);
		db.execSQL(CREATE_APP_VERSION);
		db.execSQL(CREATE_TOTAL_COUNT);
		db.execSQL(CREATE_SETTING_COMMON);
		db.execSQL(CREATE_IM_LASTEST_CHAT_OBJECT);
		db.execSQL(CREATE_IM_HISTORY_CHAT_RECORD);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// 1是初始化版本，所以不用考虑
		// 不能使用else if, 是为了解决跨版本的问题比如1直接升级到5需要执行2,3,4,5
		// newVersion在这个方法没有用到，可以做个判断newVersion == DATABASE_VERSION
		// 如果不等就抛异常（SQLite内部出现错误了，这种应该不可能出现）
		int currVersion = oldVersion;
		if (currVersion == 2) {
			// TODO执行相应的数据库变更代码
			currVersion = 3;
		}
		if (currVersion == 3) {
			// TODO执行相应的数据库变更代码
			currVersion = 4;
		}
		if (currVersion == 4) {
			// TODO执行相应的数据库变更代码
			currVersion = 5;
		}
		if (currVersion == 5) {
			// TODO执行相应的数据库变更代码
		}
	}

}