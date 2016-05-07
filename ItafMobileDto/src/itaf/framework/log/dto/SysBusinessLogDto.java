package itaf.framework.log.dto;

// Generated Sep 2, 2014 4:45:05 PM by Hibernate Tools 3.4.0.CR1

import itaf.framework.base.dto.BaseDto;

/**
 * 
 * 系统业务日志
 * 
 * @author
 * 
 * @UpdateDate 2014年9月4日
 */
public class SysBusinessLogDto extends BaseDto {


	/**
	 * 
	 */
	private static final long serialVersionUID = 8650388931963702821L;
	private String appName;
	private String logInfo;
	private String moduleName;
	private String shortMessage;
	private String objectClassName;
	private Long operatorId;
	private Long recId;
	private Long subRecId;
	private Long tcRecTypeId;
	private Long tcSubRecTypeId;

	public SysBusinessLogDto() {
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getLogInfo() {
		return logInfo;
	}

	public void setLogInfo(String logInfo) {
		this.logInfo = logInfo;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getShortMessage() {
		return shortMessage;
	}

	public void setShortMessage(String shortMessage) {
		this.shortMessage = shortMessage;
	}

	public String getObjectClassName() {
		return objectClassName;
	}

	public void setObjectClassName(String objectClassName) {
		this.objectClassName = objectClassName;
	}

	public Long getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(Long operatorId) {
		this.operatorId = operatorId;
	}

	public Long getRecId() {
		return recId;
	}

	public void setRecId(Long recId) {
		this.recId = recId;
	}

	public Long getSubRecId() {
		return subRecId;
	}

	public void setSubRecId(Long subRecId) {
		this.subRecId = subRecId;
	}

	public Long getTcRecTypeId() {
		return tcRecTypeId;
	}

	public void setTcRecTypeId(Long tcRecTypeId) {
		this.tcRecTypeId = tcRecTypeId;
	}

	public Long getTcSubRecTypeId() {
		return tcSubRecTypeId;
	}

	public void setTcSubRecTypeId(Long tcSubRecTypeId) {
		this.tcSubRecTypeId = tcSubRecTypeId;
	}

}
