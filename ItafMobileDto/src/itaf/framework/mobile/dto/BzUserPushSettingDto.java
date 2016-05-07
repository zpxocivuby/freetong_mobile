package itaf.framework.mobile.dto;

// Generated Sep 2, 2014 4:45:05 PM by Hibernate Tools 3.4.0.CR1

import itaf.framework.base.dto.BaseDto;
import itaf.framework.platform.dto.SysUserDto;

/**
 * 
 * 推送设置
 *
 * @author
 *
 * @UpdateDate 2014年9月4日
 */
public class BzUserPushSettingDto extends BaseDto {


	/**
	 * 
	 */
	private static final long serialVersionUID = -6172738565108819236L;
	private SysUserDto sysUserDto;
	private String type;
	private String status;
	private String userCode;

	public BzUserPushSettingDto() {
	}

	public SysUserDto getSysUserDto() {
		return sysUserDto;
	}

	public void setSysUserDto(SysUserDto sysUserDto) {
		this.sysUserDto = sysUserDto;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

}
