package itaf.framework.log.dto;

// Generated Sep 2, 2014 4:45:05 PM by Hibernate Tools 3.4.0.CR1

import itaf.framework.base.dto.BaseDto;

import java.util.Date;

/**
 * 
 * 接口交互日志详细信息表
 * 
 * @author
 * 
 * @UpdateDate 2014年9月4日
 */
public class SysDataInteractInfoLogDto extends BaseDto {


	/**
	 * 
	 */
	private static final long serialVersionUID = -4524525715705154077L;
	private SysDataInteractLogDto sysDataInteractLogDto;
	private String interfaceNo;
	private String interfaceName;
	private String interfaceDesc;
	private Date receiveTime;
	private Long resendCount;
	private Date lastResendTime;
	private String resendNo;

	public SysDataInteractInfoLogDto() {
	}

	public SysDataInteractLogDto getSysDataInteractLogDto() {
		return sysDataInteractLogDto;
	}

	public void setSysDataInteractLogDto(
			SysDataInteractLogDto sysDataInteractLogDto) {
		this.sysDataInteractLogDto = sysDataInteractLogDto;
	}

	public String getInterfaceNo() {
		return interfaceNo;
	}

	public void setInterfaceNo(String interfaceNo) {
		this.interfaceNo = interfaceNo;
	}

	public String getInterfaceName() {
		return interfaceName;
	}

	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}

	public String getInterfaceDesc() {
		return interfaceDesc;
	}

	public void setInterfaceDesc(String interfaceDesc) {
		this.interfaceDesc = interfaceDesc;
	}

	public Date getReceiveTime() {
		return receiveTime;
	}

	public void setReceiveTime(Date receiveTime) {
		this.receiveTime = receiveTime;
	}

	public Long getResendCount() {
		return resendCount;
	}

	public void setResendCount(Long resendCount) {
		this.resendCount = resendCount;
	}

	public Date getLastResendTime() {
		return lastResendTime;
	}

	public void setLastResendTime(Date lastResendTime) {
		this.lastResendTime = lastResendTime;
	}

	public String getResendNo() {
		return resendNo;
	}

	public void setResendNo(String resendNo) {
		this.resendNo = resendNo;
	}

}
