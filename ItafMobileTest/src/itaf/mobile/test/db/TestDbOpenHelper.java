package itaf.mobile.test.db;

import itaf.mobile.ds.db.base.DbOpenHelper;
import android.test.AndroidTestCase;

/**
 * 执行创建数据库和创建表
 * 
 * @author XININ
 * 
 * @updateDate 2013-6-26
 */
public class TestDbOpenHelper extends AndroidTestCase {

	public void testCreateDB() throws Throwable {
		DbOpenHelper dbOpenHelper = new DbOpenHelper(this.getContext());
		dbOpenHelper.getWritableDatabase();// 第一次调用该方法就会创建数据库
	}

	public void testUpdateDB() throws Throwable {
		DbOpenHelper dbOpenHelper = new DbOpenHelper(this.getContext());
		dbOpenHelper.getWritableDatabase();// 第一次调用该方法就会创建数据库
	}

}