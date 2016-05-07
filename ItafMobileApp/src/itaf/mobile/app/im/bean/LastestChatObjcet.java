package itaf.mobile.app.im.bean;

import java.util.Date;

/**
 * 最近聊天对象历史
 * 
 * @author
 * 
 */
public class LastestChatObjcet {

	public static final int CHAT = 1;
	public static final int CHAT_ROOM = 2;
	public static final int CHAT_INVITATION = 3;
	public static final int CHAT_ROOM_INVITATION = 4;
	public static final String CHAT_ROOM_STATUS = "聊天室";

	private String headUrl;

	private String jid;

	private String nickname;

	private String status;

	private String lastestChatContent;

	private Date lastestChatDateTime;

	private int type;

	private String username;

	public String getHeadUrl() {
		return headUrl;
	}

	public void setHeadUrl(String headUrl) {
		this.headUrl = headUrl;
	}

	public String getJid() {
		return jid;
	}

	public void setJid(String jid) {
		this.jid = jid;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getLastestChatContent() {
		return lastestChatContent;
	}

	public void setLastestChatContent(String lastestChatContent) {
		this.lastestChatContent = lastestChatContent;
	}

	public Date getLastestChatDateTime() {
		return lastestChatDateTime;
	}

	public void setLastestChatDateTime(Date lastestChatDateTime) {
		this.lastestChatDateTime = lastestChatDateTime;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
