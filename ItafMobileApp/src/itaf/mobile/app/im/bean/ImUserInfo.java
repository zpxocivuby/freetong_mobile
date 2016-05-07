package itaf.mobile.app.im.bean;

import java.io.Serializable;
import java.util.Date;

public class ImUserInfo implements Serializable {

	private static final long serialVersionUID = -5825059154493338063L;

	private String jid;
	// 昵称
	private String nickname;
	// 用户名
	private String username;
	// 个性签名
	private String personalSign;
	// 头像地址
	private String headUrl;
	// 状态
	private String status;
	// 最后登录时间
	private Date lastLoginDateTime;
	// 最后退出时间
	private Date lastLogoffDateTime;
	// 是否选中
	private Boolean checked;

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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPersonalSign() {
		return personalSign;
	}

	public void setPersonalSign(String personalSign) {
		this.personalSign = personalSign;
	}

	public String getHeadUrl() {
		return headUrl;
	}

	public void setHeadUrl(String headUrl) {
		this.headUrl = headUrl;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getLastLoginDateTime() {
		return lastLoginDateTime;
	}

	public void setLastLoginDateTime(Date lastLoginDateTime) {
		this.lastLoginDateTime = lastLoginDateTime;
	}

	public Date getLastLogoffDateTime() {
		return lastLogoffDateTime;
	}

	public void setLastLogoffDateTime(Date lastLogoffDateTime) {
		this.lastLogoffDateTime = lastLogoffDateTime;
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((jid == null) ? 0 : jid.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ImUserInfo other = (ImUserInfo) obj;
		if (jid == null) {
			if (other.jid != null)
				return false;
		} else if (!jid.equals(other.jid))
			return false;
		return true;
	}

}
