package itaf.framework.mobile.dto;

// Generated Sep 2, 2014 4:45:05 PM by Hibernate Tools 3.4.0.CR1

import itaf.framework.base.dto.BaseDto;
import itaf.framework.platform.dto.SysUserDto;

import java.util.Date;

/**
 * 
 * 微博绑定信息
 * 
 * @author
 * 
 * @UpdateDate 2014年9月4日
 */
public class BzWeiboBindingDto extends BaseDto {


	/**
	 * 
	 */
	private static final long serialVersionUID = -1435681843753109597L;
	private SysUserDto sysUserDto;
	private Long type;
	private String account;
	private String password;
	private String token;
	private Date accessToken;
	private Long forceLogin;

	public BzWeiboBindingDto() {
	}

	public SysUserDto getSysUserDto() {
		return sysUserDto;
	}

	public void setSysUserDto(SysUserDto sysUserDto) {
		this.sysUserDto = sysUserDto;
	}

	public Long getType() {
		return type;
	}

	public void setType(Long type) {
		this.type = type;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Date getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(Date accessToken) {
		this.accessToken = accessToken;
	}

	public Long getForceLogin() {
		return forceLogin;
	}

	public void setForceLogin(Long forceLogin) {
		this.forceLogin = forceLogin;
	}

}
