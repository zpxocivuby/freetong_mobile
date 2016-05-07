package itaf.framework.mobile.dto;

// Generated Sep 2, 2014 4:45:05 PM by Hibernate Tools 3.4.0.CR1

import itaf.framework.base.dto.BaseDto;
import itaf.framework.platform.dto.SysUserDto;

/**
 * 
 * 通用设置
 *
 * @author
 *
 * @UpdateDate 2014年9月4日
 */
public class BzUserCommonSettingDto extends BaseDto {


	/**
	 * 
	 */
	private static final long serialVersionUID = -2651273235658710135L;
	private SysUserDto sysUserDto;
	private String value;
	private String type;
	private String userCode;

	public BzUserCommonSettingDto() {
	}

	public SysUserDto getSysUserDto() {
		return sysUserDto;
	}

	public void setSysUserDto(SysUserDto sysUserDto) {
		this.sysUserDto = sysUserDto;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

}
