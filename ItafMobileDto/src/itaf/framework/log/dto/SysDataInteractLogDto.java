package itaf.framework.log.dto;

// Generated Sep 2, 2014 4:45:05 PM by Hibernate Tools 3.4.0.CR1

import itaf.framework.base.dto.BaseDto;
import itaf.framework.platform.dto.SysInfDefineDto;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * 
 * 接口交互日志
 * 
 * @author
 * 
 * @UpdateDate 2014年9月4日
 */

public class SysDataInteractLogDto extends BaseDto {


	/**
	 * 
	 */
	private static final long serialVersionUID = 4130137265677015949L;
	private SysInfDefineDto sysInfDefineDto;
	private String intfaceTranceNo;
	private Date interactTime;
	private Long isSuccess;
	private Long resendCount;
	private String lastRequestNo;
	private Date lastRequestTime;
	private Set<SysDataInteractInfoLogDto> sysDataInteractInfoLogDtos = new HashSet<SysDataInteractInfoLogDto>(
			0);

	public SysDataInteractLogDto() {
	}

	public SysInfDefineDto getSysInfDefineDto() {
		return sysInfDefineDto;
	}

	public void setSysInfDefineDto(SysInfDefineDto sysInfDefineDto) {
		this.sysInfDefineDto = sysInfDefineDto;
	}

	public String getIntfaceTranceNo() {
		return intfaceTranceNo;
	}

	public void setIntfaceTranceNo(String intfaceTranceNo) {
		this.intfaceTranceNo = intfaceTranceNo;
	}

	public Date getInteractTime() {
		return interactTime;
	}

	public void setInteractTime(Date interactTime) {
		this.interactTime = interactTime;
	}

	public Long getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(Long isSuccess) {
		this.isSuccess = isSuccess;
	}

	public Long getResendCount() {
		return resendCount;
	}

	public void setResendCount(Long resendCount) {
		this.resendCount = resendCount;
	}

	public String getLastRequestNo() {
		return lastRequestNo;
	}

	public void setLastRequestNo(String lastRequestNo) {
		this.lastRequestNo = lastRequestNo;
	}

	public Date getLastRequestTime() {
		return lastRequestTime;
	}

	public void setLastRequestTime(Date lastRequestTime) {
		this.lastRequestTime = lastRequestTime;
	}

	public Set<SysDataInteractInfoLogDto> getSysDataInteractInfoLogDtos() {
		return sysDataInteractInfoLogDtos;
	}

	public void setSysDataInteractInfoLogDtos(
			Set<SysDataInteractInfoLogDto> sysDataInteractInfoLogDtos) {
		this.sysDataInteractInfoLogDtos = sysDataInteractInfoLogDtos;
	}

}
