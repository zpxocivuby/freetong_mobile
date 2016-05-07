package itaf.mobile.ds.domain;

import java.util.Date;

/**
 * 登陆历史记录
 * 
 * 
 * @author
 * 
 * @updateDate 2014年1月2日
 */
public class LoginHistoryRecord {

	// ID
	private Long id;
	// 用户名
	private String username;
	// 密码
	private String password;
	// 电话号码
	private String mobile;
	// 记住密码
	private int rememberPassword;
	// 自动登录
	private int autoLogin;
	// 登陆时间
	private Date loginTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public int getRememberPassword() {
		return rememberPassword;
	}

	public void setRememberPassword(int rememberPassword) {
		this.rememberPassword = rememberPassword;
	}

	public int getAutoLogin() {
		return autoLogin;
	}

	public void setAutoLogin(int autoLogin) {
		this.autoLogin = autoLogin;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

}
