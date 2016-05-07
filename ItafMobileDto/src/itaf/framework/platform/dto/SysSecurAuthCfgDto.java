package itaf.framework.platform.dto;

// Generated Sep 2, 2014 4:45:05 PM by Hibernate Tools 3.4.0.CR1

import itaf.framework.base.dto.BaseDto;
import itaf.framework.log.dto.SysSecurAuthCfgLogDto;

import java.util.HashSet;
import java.util.Set;

/**
 * 
 * 系统安全验证生效配置
 * 
 * @author
 * 
 * @UpdateDate 2014年9月4日
 */
public class SysSecurAuthCfgDto extends BaseDto {


	/**
	 * 
	 */
	private static final long serialVersionUID = -3356176290121731379L;
	private String authType;
	private Boolean isEnable;
	private Set<SysSecurAuthCfgLogDto> sysSecurAuthCfgLogDtos = new HashSet<SysSecurAuthCfgLogDto>(
			0);

	public SysSecurAuthCfgDto() {
	}

	public String getAuthType() {
		return authType;
	}

	public void setAuthType(String authType) {
		this.authType = authType;
	}

	public Boolean getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(Boolean isEnable) {
		this.isEnable = isEnable;
	}

	public Set<SysSecurAuthCfgLogDto> getSysSecurAuthCfgLogDtos() {
		return sysSecurAuthCfgLogDtos;
	}

	public void setSysSecurAuthCfgLogDtos(
			Set<SysSecurAuthCfgLogDto> sysSecurAuthCfgLogDtos) {
		this.sysSecurAuthCfgLogDtos = sysSecurAuthCfgLogDtos;
	}


}
