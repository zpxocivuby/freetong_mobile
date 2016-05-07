package itaf.framework.platform.dto;

// Generated Sep 2, 2014 4:45:05 PM by Hibernate Tools 3.4.0.CR1

import itaf.framework.base.dto.BaseDto;

/**
 * 
 * 黑名单
 *
 * @author
 *
 * @UpdateDate 2014年9月4日
 */
public class SysBlacklistDto extends BaseDto {


	/**
	 * 
	 */
	private static final long serialVersionUID = 5753300022401038895L;
	private SysUserDto sysUserDto;
	private String status;
	private String logincode;
	private String realnameZh;
	private String realnameEn;
	private String mobile;

	public SysBlacklistDto() {
	}

	public SysBlacklistDto(String logincode) {
		this.logincode = logincode;
	}

	public SysUserDto getSysUserDto() {
		return sysUserDto;
	}

	public void setSysUserDto(SysUserDto sysUserDto) {
		this.sysUserDto = sysUserDto;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getLogincode() {
		return logincode;
	}

	public void setLogincode(String logincode) {
		this.logincode = logincode;
	}

	public String getRealnameZh() {
		return realnameZh;
	}

	public void setRealnameZh(String realnameZh) {
		this.realnameZh = realnameZh;
	}

	public String getRealnameEn() {
		return realnameEn;
	}

	public void setRealnameEn(String realnameEn) {
		this.realnameEn = realnameEn;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}


}
