/**
 * Copyright 2015 Freetong
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package itaf.mobile.app.im.db;

import itaf.framework.base.dto.WsPageResult;
import itaf.mobile.app.im.bean.LastestChatObjcet;
import itaf.mobile.core.utils.DateUtil;
import itaf.mobile.ds.db.base.BaseTempleteDaoImpl;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;

/**
 * 
 * 
 * 
 * @author
 * 
 * @updateDate 2013年12月31日
 */
public class LastestChatObjcetDb extends BaseTempleteDaoImpl<LastestChatObjcet> {

	public LastestChatObjcetDb(Context context) {
		super(context);
	}

	public void saveOrUpdate(LastestChatObjcet chatObj) {
		String sql = "REPLACE INTO [IM_LASTEST_CHAT_OBJECT] ([HEAD_URL],[JID],[NICKNAME],[STATUS],[LASTEST_CHAT_CONTENT],[LASTEST_CHAT_DATETIME],[TYPE],[CURR_USERNAME]) VALUES(?,?,?,?,?,?,?,?) ";
		Object[] params = new Object[] {
				chatObj.getHeadUrl(),
				chatObj.getJid(),
				chatObj.getNickname(),
				chatObj.getStatus(),
				chatObj.getLastestChatContent(),
				DateUtil.formatDate(chatObj.getLastestChatDateTime(),
						DateUtil.FORMAT_DATETIME_DEFAULT), chatObj.getType(),
				chatObj.getUsername() };
		this.execSql(sql, params);
	}

	public void deleteByJid(String jid, String username) {
		String sqlObj = "DELETE FROM [IM_LASTEST_CHAT_OBJECT] WHERE [JID] = ? AND [CURR_USERNAME] = ?";
		Object[] sqlParam = new Object[] { jid, username };
		this.execSql(sqlObj, sqlParam);
	}

	public void deleteAndRecordByJid(String jid, String username) {
		List<String> sqls = new ArrayList<String>();
		List<Object[]> params = new ArrayList<Object[]>();
		String sqlObj = "DELETE FROM [IM_LASTEST_CHAT_OBJECT] WHERE [JID] = ? AND [CURR_USERNAME] = ?";
		Object[] sqlParam = new Object[] { jid, username };
		sqls.add(sqlObj);
		params.add(sqlParam);
		String sqlRecord = "DELETE FROM [HISTORY_CHAT_RECORD] WHERE [JID] = ? AND [CURR_USERNAME] = ?";
		sqls.add(sqlRecord);
		params.add(sqlParam);
		this.execSqls(sqls, params);
	}

	public LastestChatObjcet findByRoomJid(String roomJid, String username) {
		LastestChatObjcet result = new LastestChatObjcet();
		String sql = "SELECT [CURR_USERNAME],[HEAD_URL],[JID],[NICKNAME],[STATUS],[LASTEST_CHAT_CONTENT],[LASTEST_CHAT_DATETIME],[TYPE]"
				+ " FROM [IM_LASTEST_CHAT_OBJECT] WHERE [JID] = ? AND [CURR_USERNAME] = ?";
		String[] sqlParams = new String[] { roomJid, username };
		result = (LastestChatObjcet) this.query(sql, sqlParams, 2);
		return result;
	}

	@SuppressWarnings("unchecked")
	public WsPageResult<LastestChatObjcet> findPager(String username,
			Integer currentIndex, Integer pageSize) {
		WsPageResult<LastestChatObjcet> result = new WsPageResult<LastestChatObjcet>();
		result.setPageSize(pageSize);
		result.setCurrentIndex(currentIndex);
		String sql = "SELECT [CURR_USERNAME],[HEAD_URL],[JID],[NICKNAME],[STATUS],[LASTEST_CHAT_CONTENT],[LASTEST_CHAT_DATETIME],[TYPE]"
				+ " FROM [IM_LASTEST_CHAT_OBJECT] WHERE [CURR_USERNAME] = ? ORDER BY [LASTEST_CHAT_DATETIME] DESC LIMIT ? OFFSET ?";
		String[] sqlParams = new String[] { username, pageSize.toString(),
				currentIndex.toString() };
		result.setContent((List<LastestChatObjcet>) this.query(sql, sqlParams,
				1));
		String sqlCount = "SELECT COUNT(*) FROM [IM_LASTEST_CHAT_OBJECT] WHERE [CURR_USERNAME] = ?";
		String[] sqlCountParams = new String[] { username };
		result.setTotalCount(this.queryCount(sqlCount, sqlCountParams));
		return result;
	}

	@Override
	public Object processCursor(Cursor cursor, int cursorKey) {
		Object result = null;
		switch (cursorKey) {
		case 1:
			List<LastestChatObjcet> recordList = new ArrayList<LastestChatObjcet>();
			while (cursor.moveToNext()) {
				LastestChatObjcet temp = new LastestChatObjcet();
				temp.setUsername(getStringFromCursor(cursor, "CURR_USERNAME"));
				temp.setHeadUrl(getStringFromCursor(cursor, "HEAD_URL"));
				temp.setJid(getStringFromCursor(cursor, "JID"));
				temp.setNickname(getStringFromCursor(cursor, "NICKNAME"));
				temp.setStatus(getStringFromCursor(cursor, "STATUS"));
				temp.setLastestChatContent(getStringFromCursor(cursor,
						"LASTEST_CHAT_CONTENT"));
				temp.setLastestChatDateTime(getDateFromCursor(cursor,
						"LASTEST_CHAT_DATETIME",
						DateUtil.FORMAT_DATETIME_DEFAULT));
				temp.setType(getIntegerFromCursor(cursor, "TYPE"));
				recordList.add(temp);
			}
			result = recordList;
			break;
		case 2:
			LastestChatObjcet chatObj = null;
			if (cursor.moveToNext()) {
				chatObj = new LastestChatObjcet();
				chatObj.setUsername(getStringFromCursor(cursor, "CURR_USERNAME"));
				chatObj.setHeadUrl(getStringFromCursor(cursor, "HEAD_URL"));
				chatObj.setJid(getStringFromCursor(cursor, "JID"));
				chatObj.setNickname(getStringFromCursor(cursor, "NICKNAME"));
				chatObj.setStatus(getStringFromCursor(cursor, "STATUS"));
				chatObj.setLastestChatContent(getStringFromCursor(cursor,
						"LASTEST_CHAT_CONTENT"));
				chatObj.setLastestChatDateTime(getDateFromCursor(cursor,
						"LASTEST_CHAT_DATETIME",
						DateUtil.FORMAT_DATETIME_DEFAULT));
				chatObj.setType(getIntegerFromCursor(cursor, "TYPE"));
			}
			result = chatObj;
			break;
		}
		return result;
	}

}
