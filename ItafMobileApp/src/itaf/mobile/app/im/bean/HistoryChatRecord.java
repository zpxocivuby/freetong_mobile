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
package itaf.mobile.app.im.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * 聊天内容
 * 
 * @author
 * 
 * @update 2013年10月23日
 */
public class HistoryChatRecord implements Serializable {

	private static final long serialVersionUID = -6531936319609684424L;

	public static final String CHAT_TYPE_INVITATION = "invitation";
	public static final String CHAT_TYPE_FROM = "from";
	public static final String CHAT_TYPE_TO = "to";
	public static final String CHAT_TYPE_OFFLINE = "offline";
	public static final String CHAT_TYPE_NOREAD = "noread";

	// 用户名
	private String jid;
	// 昵称或者备注
	private String nickname;
	// 头像地址
	private String headUrl;
	// 聊天内容
	private String chatContent;
	// 聊天时间
	private Date chatTime;
	// from 或者 to
	private String chatType;
	private String username;

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

	public String getHeadUrl() {
		return headUrl;
	}

	public void setHeadUrl(String headUrl) {
		this.headUrl = headUrl;
	}

	public String getChatContent() {
		return chatContent;
	}

	public void setChatContent(String chatContent) {
		this.chatContent = chatContent;
	}

	public Date getChatTime() {
		return chatTime;
	}

	public void setChatTime(Date chatTime) {
		this.chatTime = chatTime;
	}

	public String getChatType() {
		return chatType;
	}

	public void setChatType(String chatType) {
		this.chatType = chatType;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
