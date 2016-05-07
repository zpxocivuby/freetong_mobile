package itaf.framework.platform.dto;

// Generated Sep 2, 2014 4:45:05 PM by Hibernate Tools 3.4.0.CR1

import itaf.framework.base.dto.BaseDto;
import itaf.framework.log.dto.SysDataInteractLogDto;

import java.util.HashSet;
import java.util.Set;

/**
 * 
 * 系统接口定义
 * 
 * @author
 * 
 * @UpdateDate 2014年9月4日
 */
public class SysInfDefineDto extends BaseDto {


	/**
	 * 
	 */
	private static final long serialVersionUID = -5478051961955548938L;
	private String interfaceName;
	private String interfaceNo;
	private String sender;
	private String receiver;
	private String messageType;
	private String interfaceType;
	private String webserviceUrl;
	private String mqueue;
	private String processClass;
	private Long logEnabled;
	private Long resendEnabled;
	private String beanImplClassName;
	private Set<SysDataInteractLogDto> sysDataInteractLogDtos = new HashSet<SysDataInteractLogDto>(
			0);
	private Set<SysInfParamDefineDto> sysInfParamDefineDtos = new HashSet<SysInfParamDefineDto>(
			0);
	private Set<SysInfFieldDefineDto> sysInfFieldDefineDtos = new HashSet<SysInfFieldDefineDto>(
			0);

	public SysInfDefineDto() {
	}

	public String getInterfaceName() {
		return interfaceName;
	}

	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}

	public String getInterfaceNo() {
		return interfaceNo;
	}

	public void setInterfaceNo(String interfaceNo) {
		this.interfaceNo = interfaceNo;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	public String getInterfaceType() {
		return interfaceType;
	}

	public void setInterfaceType(String interfaceType) {
		this.interfaceType = interfaceType;
	}

	public String getWebserviceUrl() {
		return webserviceUrl;
	}

	public void setWebserviceUrl(String webserviceUrl) {
		this.webserviceUrl = webserviceUrl;
	}

	public String getMqueue() {
		return mqueue;
	}

	public void setMqueue(String mqueue) {
		this.mqueue = mqueue;
	}

	public String getProcessClass() {
		return processClass;
	}

	public void setProcessClass(String processClass) {
		this.processClass = processClass;
	}

	public Long getLogEnabled() {
		return logEnabled;
	}

	public void setLogEnabled(Long logEnabled) {
		this.logEnabled = logEnabled;
	}

	public Long getResendEnabled() {
		return resendEnabled;
	}

	public void setResendEnabled(Long resendEnabled) {
		this.resendEnabled = resendEnabled;
	}

	public String getBeanImplClassName() {
		return beanImplClassName;
	}

	public void setBeanImplClassName(String beanImplClassName) {
		this.beanImplClassName = beanImplClassName;
	}

	public Set<SysDataInteractLogDto> getSysDataInteractLogDtos() {
		return sysDataInteractLogDtos;
	}

	public void setSysDataInteractLogDtos(
			Set<SysDataInteractLogDto> sysDataInteractLogDtos) {
		this.sysDataInteractLogDtos = sysDataInteractLogDtos;
	}

	public Set<SysInfParamDefineDto> getSysInfParamDefineDtos() {
		return sysInfParamDefineDtos;
	}

	public void setSysInfParamDefineDtos(
			Set<SysInfParamDefineDto> sysInfParamDefineDtos) {
		this.sysInfParamDefineDtos = sysInfParamDefineDtos;
	}

	public Set<SysInfFieldDefineDto> getSysInfFieldDefineDtos() {
		return sysInfFieldDefineDtos;
	}

	public void setSysInfFieldDefineDtos(
			Set<SysInfFieldDefineDto> sysInfFieldDefineDtos) {
		this.sysInfFieldDefineDtos = sysInfFieldDefineDtos;
	}


}
