package itaf.mobile.app.im.db;

import itaf.framework.base.dto.WsPageResult;
import itaf.mobile.app.im.bean.HistoryChatRecord;
import itaf.mobile.core.utils.DateUtil;
import itaf.mobile.ds.db.base.BaseTempleteDaoImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.database.Cursor;

/**
 * 历史聊天记录
 * 
 * 
 * @author
 * 
 * @updateDate 2013年12月27日
 */
public class HistoryChatRecordDb extends BaseTempleteDaoImpl<HistoryChatRecord> {

	public HistoryChatRecordDb(Context context) {
		super(context);
	}

	/**
	 * 保存历史聊天记录
	 * 
	 * @param record
	 *            HistoryChatRecord
	 */
	public void save(HistoryChatRecord record) {
		String sql = "INSERT INTO [IM_HISTORY_CHAT_RECORD] ([HEAD_URL],[JID],[NICKNAME],[CHAT_CONTENT],[CHAT_TIME],[CHAT_TYPE],[CURR_USERNAME]) VALUES(?,?,?,?,?,?,?) ";
		Object[] sqlParam = new Object[] {
				record.getHeadUrl(),
				record.getJid(),
				record.getNickname(),
				record.getChatContent(),
				DateUtil.formatDate(record.getChatTime(),
						DateUtil.FORMAT_DATETIME_DEFAULT),
				record.getChatType(), record.getUsername() };
		this.execSql(sql, sqlParam);
	}

	/**
	 * 删除默认的历史聊天记录
	 * 
	 * @param jid
	 *            JID
	 * @param username
	 *            当前用户名
	 */
	public void deleteByJid(String jid, String username) {
		String sql = "DELETE FROM [IM_HISTORY_CHAT_RECORD] WHERE [JID] = ? AND [CURR_USERNAME] = ?";
		Object[] sqlParam = new Object[] { jid, username };
		this.execSql(sql, sqlParam);
	}

	/**
	 * 消息状态为OFFLINE更新为NOREAD
	 * 
	 * @param username
	 *            当前用户名
	 */
	public void updateChatTypeToNoRead(String username) {
		String sql = "UPDATE [IM_HISTORY_CHAT_RECORD] SET [CHAT_TYPE] =?  WHERE [CURR_USERNAME] = ? AND [CHAT_TYPE] = ?";
		Object[] sqlParam = new Object[] { HistoryChatRecord.CHAT_TYPE_NOREAD,
				username, HistoryChatRecord.CHAT_TYPE_OFFLINE };
		this.execSql(sql, sqlParam);
	}

	/**
	 * 消息状态为OFFLINE和NOREAD为TO，用于进入聊天窗口
	 * 
	 * @param jid
	 *            JID
	 * @param username
	 *            当前用户名
	 */
	public void updateChatTypeByJid(String jid, String username) {
		String sql = "UPDATE [IM_HISTORY_CHAT_RECORD] SET [CHAT_TYPE] =?  WHERE [JID] = ? AND [CURR_USERNAME] = ? AND ([CHAT_TYPE] = ? or [CHAT_TYPE] = ?)";
		Object[] sqlParam = new Object[] { HistoryChatRecord.CHAT_TYPE_TO, jid,
				username, HistoryChatRecord.CHAT_TYPE_OFFLINE,
				HistoryChatRecord.CHAT_TYPE_NOREAD };
		this.execSql(sql, sqlParam);
	}

	/**
	 * 分组查找好友的离线消息总数
	 * 
	 * @param username
	 *            当前用户名
	 * @return Map<String, Integer> KEY=JID;VALUE=COUNT
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Integer> findOfflineByGourp(String username) {
		String sql = "Select [JID], COUNT([JID]) AS [RECORDCOUNT] "
				+ " From [IM_HISTORY_CHAT_RECORD] where [CURR_USERNAME] = ? AND [CHAT_TYPE] = ? GROUP BY [JID]"
				+ " order by [IM_HISTORY_CHAT_RECORD_ID] DESC";
		String[] sqlParams = new String[] { username,
				HistoryChatRecord.CHAT_TYPE_OFFLINE };
		Object obj = this.query(sql, sqlParams, 3);
		return obj != null ? (Map<String, Integer>) obj : null;
	}

	/**
	 * 根据JID查找状态为OFFLINE和NOREAD的消息
	 * 
	 * @param jid
	 *            JID
	 * @param username
	 *            当前用户名
	 * @return List<HistoryChatRecord>
	 */
	@SuppressWarnings("unchecked")
	public List<HistoryChatRecord> findOfflineByJid(String jid, String username) {
		String sql = "Select [CURR_USERNAME],[HEAD_URL],[JID],[NICKNAME],[CHAT_CONTENT],[CHAT_TIME],[CHAT_TYPE] "
				+ " From [IM_HISTORY_CHAT_RECORD] where [JID] = ? AND [CURR_USERNAME] = ? AND ([CHAT_TYPE] = ? OR [CHAT_TYPE] = ? OR [CHAT_TYPE] = ?)"
				+ " order by [IM_HISTORY_CHAT_RECORD_ID] DESC";
		String[] sqlParams = new String[] { jid, username,
				HistoryChatRecord.CHAT_TYPE_OFFLINE,
				HistoryChatRecord.CHAT_TYPE_NOREAD,
				HistoryChatRecord.CHAT_TYPE_INVITATION };
		Object obj = this.query(sql, sqlParams, 1);
		return obj != null ? (List<HistoryChatRecord>) obj : null;
	}

	/**
	 * 根据JID查找状态为OFFLINE和NOREAD的消息数量
	 * 
	 * @param jid
	 *            JID
	 * @param username
	 *            当前用户名
	 * @return 状态为OFFLINE和NOREAD的消息数量
	 */
	public int findOfflineCountByJid(String jid, String username) {
		String sql = "SELECT COUNT([JID]) AS OFFLINE_COUNT FROM [IM_HISTORY_CHAT_RECORD] WHERE [JID] = ? AND [CURR_USERNAME] = ? AND ([CHAT_TYPE] = ? OR [CHAT_TYPE] = ? OR [CHAT_TYPE] = ?)";
		String[] sqlParams = new String[] { jid, username,
				HistoryChatRecord.CHAT_TYPE_OFFLINE,
				HistoryChatRecord.CHAT_TYPE_NOREAD,
				HistoryChatRecord.CHAT_TYPE_INVITATION };
		Object obj = this.query(sql, sqlParams, 4);
		return obj != null ? (Integer) obj : 0;
	}

	public int findOfflineCount(String username) {
		String sql = "SELECT COUNT([JID]) AS OFFLINE_COUNT FROM [IM_HISTORY_CHAT_RECORD] WHERE [CURR_USERNAME] = ? AND [CHAT_TYPE] = ? ";
		String[] sqlParams = new String[] { username,
				HistoryChatRecord.CHAT_TYPE_OFFLINE };
		Object obj = this.query(sql, sqlParams, 4);
		return obj != null ? (Integer) obj : 0;
	}

	public int findOfflineAndInvcationCount(String username) {
		String sql = "SELECT COUNT([JID]) AS OFFLINE_COUNT FROM [IM_HISTORY_CHAT_RECORD] WHERE [CURR_USERNAME] = ? AND ([CHAT_TYPE] = ? OR [CHAT_TYPE] = ? )";
		String[] sqlParams = new String[] { username,
				HistoryChatRecord.CHAT_TYPE_OFFLINE,
				HistoryChatRecord.CHAT_TYPE_INVITATION };
		Object obj = this.query(sql, sqlParams, 4);
		return obj != null ? (Integer) obj : 0;
	}

	/**
	 * 查找和JID最后的聊天内容
	 * 
	 * @param jid
	 *            JID
	 * @param username
	 *            当前用户名
	 * @return 最后聊天内容
	 */
	@SuppressWarnings("unchecked")
	public HistoryChatRecord findLastestChatContent(String jid, String username) {
		String sql = "Select [CURR_USERNAME],[HEAD_URL],[JID],[NICKNAME],[CHAT_CONTENT],[CHAT_TIME],[CHAT_TYPE] "
				+ " From [IM_HISTORY_CHAT_RECORD] where [JID] = ? AND [CURR_USERNAME] = ? "
				+ " order by [IM_HISTORY_CHAT_RECORD_ID] DESC LIMIT ? OFFSET ?";
		String[] sqlParams = new String[] { jid, username, "1", "0" };
		Object obj = this.query(sql, sqlParams, 1);
		if (obj != null) {
			List<HistoryChatRecord> result = (List<HistoryChatRecord>) obj;
			return result.size() > 0 ? result.get(0) : null;
		}
		return null;
	}

	/**
	 * 查找好友为JID的所有历史记录
	 * 
	 * @param jid
	 *            JID
	 * @param username
	 *            当前用户名
	 * @return List<HistoryChatRecord>
	 */
	@SuppressWarnings("unchecked")
	public List<HistoryChatRecord> findByJid(String jid, String username) {
		String sql = "Select [CURR_USERNAME],[HEAD_URL],[JID],[NICKNAME],[CHAT_CONTENT],[CHAT_TIME],[CHAT_TYPE] "
				+ " From [IM_HISTORY_CHAT_RECORD] where [JID] = ? AND [CURR_USERNAME] = ? "
				+ " order by [IM_HISTORY_CHAT_RECORD_ID] ";
		String[] sqlParams = new String[] { jid, username };
		Object obj = this.query(sql, sqlParams, 1);
		return obj != null ? (List<HistoryChatRecord>) obj : null;
	}

	/**
	 * 分页查找好友为JID的历史聊天记录
	 * 
	 * @param jid
	 *            JID
	 * @param username
	 *            当前用户名
	 * @param currentIndex
	 *            开始位置
	 * @param pageSize
	 *            多少条
	 * @return PageResult<HistoryChatRecord>
	 */
	@SuppressWarnings("unchecked")
	public WsPageResult<HistoryChatRecord> findPager(String jid,
			String username, Integer currentIndex, Integer pageSize) {
		WsPageResult<HistoryChatRecord> result = new WsPageResult<HistoryChatRecord>();
		result.setPageSize(pageSize);
		result.setCurrentIndex(currentIndex);
		String sql = "SELECT [CURR_USERNAME],[HEAD_URL],[JID],[NICKNAME],[CHAT_CONTENT],[CHAT_TIME],[CHAT_TYPE] "
				+ " FROM [IM_HISTORY_CHAT_RECORD] WHERE [JID] = ? AND [CURR_USERNAME] = ? "
				+ " ORDER BY [IM_HISTORY_CHAT_RECORD_ID] LIMIT ? OFFSET ?";
		String[] sqlParams = new String[] { jid, username, pageSize.toString(),
				currentIndex.toString() };
		result.setContent((Collection<HistoryChatRecord>) this.query(sql,
				sqlParams, 1));
		String sqlCount = "SELECT COUNT(*) FROM [IM_HISTORY_CHAT_RECORD] WHERE [JID] = ? AND [CURR_USERNAME] = ?";
		String[] sqlCountParams = new String[] { jid, username };
		result.setTotalCount(this.queryCount(sqlCount, sqlCountParams));
		return result;
	}

	@Override
	public Object processCursor(Cursor cursor, int cursorKey) {
		Object result = null;
		switch (cursorKey) {
		case 1:
			List<HistoryChatRecord> recordList = new ArrayList<HistoryChatRecord>();
			while (cursor.moveToNext()) {
				HistoryChatRecord temp = new HistoryChatRecord();
				temp.setUsername(getStringFromCursor(cursor, "CURR_USERNAME"));
				temp.setHeadUrl(getStringFromCursor(cursor, "HEAD_URL"));
				temp.setJid(getStringFromCursor(cursor, "JID"));
				temp.setNickname(getStringFromCursor(cursor, "NICKNAME"));
				temp.setChatContent(getStringFromCursor(cursor, "CHAT_CONTENT"));
				temp.setChatTime(getDateFromCursor(cursor, "CHAT_TIME",
						DateUtil.FORMAT_DATETIME_DEFAULT));
				temp.setChatType(getStringFromCursor(cursor, "CHAT_TYPE"));
				recordList.add(temp);
			}
			result = recordList;
			break;
		case 2:
			String content = "";
			if (cursor.moveToNext()) {
				content = getStringFromCursor(cursor, "CHAT_CONTENT");
			}
			result = content;
			break;
		case 3:
			Map<String, Integer> jidAndCount = new HashMap<String, Integer>();
			while (cursor.moveToNext()) {
				String key = getStringFromCursor(cursor, "JID");
				Integer value = getIntegerFromCursor(cursor, "RECORDCOUNT");
				jidAndCount.put(key, value);
			}
			result = jidAndCount;
			break;
		case 4:
			int offlinecount = 0;
			if (cursor.moveToNext()) {
				offlinecount = getIntegerFromCursor(cursor, "OFFLINE_COUNT");
			}
			result = offlinecount;
			break;
		}
		return result;
	}

}
