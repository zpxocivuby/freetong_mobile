package itaf.framework.log.dto;

// Generated Sep 2, 2014 4:45:05 PM by Hibernate Tools 3.4.0.CR1

import itaf.framework.base.dto.BaseDto;
import itaf.framework.platform.dto.SysSecurAuthCfgDto;

/**
 * 
 * 系统安全验证生效配置日志
 * 
 * @author
 * 
 * @UpdateDate 2014年9月4日
 */
public class SysSecurAuthCfgLogDto extends BaseDto {


	/**
	 * 
	 */
	private static final long serialVersionUID = 8233380943067295275L;
	private SysSecurAuthCfgDto sysSecurAuthCfgDto;
	private String modifyField;
	private String preValue;
	private String aftValue;

	public SysSecurAuthCfgLogDto() {
	}

	public SysSecurAuthCfgDto getSysSecurAuthCfgDto() {
		return sysSecurAuthCfgDto;
	}

	public void setSysSecurAuthCfgDto(SysSecurAuthCfgDto sysSecurAuthCfgDto) {
		this.sysSecurAuthCfgDto = sysSecurAuthCfgDto;
	}

	public String getModifyField() {
		return modifyField;
	}

	public void setModifyField(String modifyField) {
		this.modifyField = modifyField;
	}

	public String getPreValue() {
		return preValue;
	}

	public void setPreValue(String preValue) {
		this.preValue = preValue;
	}

	public String getAftValue() {
		return aftValue;
	}

	public void setAftValue(String aftValue) {
		this.aftValue = aftValue;
	}

}
