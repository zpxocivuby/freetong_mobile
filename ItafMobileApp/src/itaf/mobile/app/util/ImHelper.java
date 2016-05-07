package itaf.mobile.app.util;

import itaf.framework.platform.dto.SysUserDto;
import itaf.mobile.app.im.bean.ImUserInfo;
import itaf.mobile.app.im.xmpp.XmppVCardManager;
import itaf.mobile.core.app.AppApplication;
import itaf.mobile.core.app.AppConfig;
import itaf.mobile.core.bean.SessionUser;
import itaf.mobile.core.utils.DateUtil;
import itaf.mobile.core.utils.StringHelper;

import java.util.Date;

/**
 * IM工具类
 * 
 * 
 * @author
 * 
 * @update 2013年12月6日
 */
public class ImHelper {

	/** (好友Im名称) */
	public static final String IM_IMUSERNAME = "FRIEND_IMUSERNAME";

	/** (聊天室名称) */
	public static final String IM_ROOMJID = "IM_ROOMJID";
	/** (邀请者) */
	public static final String IM_INVITER = "IM_INVITER";
	/** (理由) */
	public static final String IM_REASON = "IM_REASON";

	public static final String INVITATION = "INVITATION#";

	public static String processToUsername(String jid) {
		String username = null;
		if (jid.contains("@")) {
			username = jid.split("@")[0];
		}
		return username;
	}

	public static String processToJid(String username) {
		if (username == null) {
			return null;
		}
		if (!username.contains("@" + AppConfig.imServerDomainName)) {
			username = username + "@" + AppConfig.imServerDomainName;
		}
		return username;
	}

	public static boolean isChatRoom(String roomjid) {
		if (StringHelper.isEmpty(roomjid)) {
			return false;
		}
		return roomjid.contains("@" + AppConfig.imServerName);
	}

	public static String getRoomJid(String usernmae) {
		StringBuilder sb = new StringBuilder();
		sb.append(usernmae);
		sb.append("_");
		sb.append(DateUtil.formatDate(new Date(),
				DateUtil.FORMAT_DATETIME_YYYYMMDDHHMMSS));
		sb.append("@");
		sb.append(AppConfig.imServerName);
		return sb.toString();
	}

	public static String processToRoomJid(String roomSubject) {
		if (roomSubject == null) {
			return null;
		}
		if (!roomSubject.contains("@" + AppConfig.imServerName)) {
			roomSubject = roomSubject + "@" + AppConfig.imServerName;
		}
		return roomSubject;
	}

	public static String processToRoomSubject(String jid) {
		if (jid == null) {
			return null;
		}
		if (jid.contains("@" + AppConfig.imServerName)) {
			jid = jid.substring(0, jid.indexOf("@" + AppConfig.imServerName));
		}
		return jid;
	}

	public static String getCurrRoomNickname() {
		String username = AppApplication.getInstance().getSessionUser()
				.getUsername();
		return XmppVCardManager.getInstance().getNickname(
				ImHelper.processToJid(username))
				+ "(" + username + ")";
	}

	public static String generationNickname(SessionUser sessionUser) {
		if (StringHelper.isNotEmpty(sessionUser.getRealname())) {
			return sessionUser.getRealname();
		}
		if (StringHelper.isNotEmpty(sessionUser.getRealname())) {
			return sessionUser.getRealname();
		}
		return sessionUser.getUsername();
	}

	public static ImUserInfo processToImUserInfo(SysUserDto userDto) {
		ImUserInfo result = null;
		if (userDto == null) {
			return result;
		}
		result = new ImUserInfo();
		// result.setUsername(userDto.getUsername());
		// result.setPersonalSign(userDto.getPersonalSign());
		// result.setHeadUrl(userDto.getHeadUrl());
		// result.setJid(processToJid(userDto.getUsername()));
		// result.setNickname(generationNickname(userDto.getRealnameZh(),
		// userDto.getRealnameEn(), userDto.getUsername()));
		// result.setPersonalSign(userDto.getPersonalSign());
		return result;
	}

}
