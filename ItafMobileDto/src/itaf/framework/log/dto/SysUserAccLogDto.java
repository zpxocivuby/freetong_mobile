package itaf.framework.log.dto;

// Generated Sep 2, 2014 4:45:05 PM by Hibernate Tools 3.4.0.CR1

import itaf.framework.base.dto.BaseDto;

import java.util.Date;

/**
 * 
 * 用户访问日志
 * 
 * @author
 * 
 * @UpdateDate 2014年9月4日
 */
public class SysUserAccLogDto extends BaseDto {


	/**
	 * 
	 */
	private static final long serialVersionUID = 8995284822551526032L;

	public static final String LOGIN_RESULT_SUCCESS = "success";

	public static final String LOGIN_RESULT_FAILS = "fails";

	private long sysUserId;
	private String remoteAddr;
	private String fromIp;
	private String sessionId;
	private String username;
	private String loginResult;
	private Long tryCount;
	private Date loginTime;
	private Date logoutTime;

	public SysUserAccLogDto() {
	}

	public long getSysUserId() {
		return sysUserId;
	}

	public void setSysUserId(long sysUserId) {
		this.sysUserId = sysUserId;
	}

	public String getRemoteAddr() {
		return remoteAddr;
	}

	public void setRemoteAddr(String remoteAddr) {
		this.remoteAddr = remoteAddr;
	}

	public String getFromIp() {
		return fromIp;
	}

	public void setFromIp(String fromIp) {
		this.fromIp = fromIp;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getLoginResult() {
		return loginResult;
	}

	public void setLoginResult(String loginResult) {
		this.loginResult = loginResult;
	}

	public Long getTryCount() {
		return tryCount;
	}

	public void setTryCount(Long tryCount) {
		this.tryCount = tryCount;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public Date getLogoutTime() {
		return logoutTime;
	}

	public void setLogoutTime(Date logoutTime) {
		this.logoutTime = logoutTime;
	}

}
