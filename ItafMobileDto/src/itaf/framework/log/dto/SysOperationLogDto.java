package itaf.framework.log.dto;

// Generated Sep 2, 2014 4:45:05 PM by Hibernate Tools 3.4.0.CR1

import itaf.framework.base.dto.BaseDto;

import java.util.Date;

/**
 * 
 * 系统操作日志
 * 
 * @author
 * 
 * @UpdateDate 2014年9月4日
 */
public class SysOperationLogDto extends BaseDto {


	/**
	 * 
	 */
	private static final long serialVersionUID = 3904576335472140933L;
	private String username;
	private String resName;
	private Date operationTime;
	private Long isSuccess;
	private Long tmSysErrorCodeId;
	private String errorImpact;

	public SysOperationLogDto() {
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getResName() {
		return resName;
	}

	public void setResName(String resName) {
		this.resName = resName;
	}

	public Date getOperationTime() {
		return operationTime;
	}

	public void setOperationTime(Date operationTime) {
		this.operationTime = operationTime;
	}

	public Long getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(Long isSuccess) {
		this.isSuccess = isSuccess;
	}

	public Long getTmSysErrorCodeId() {
		return tmSysErrorCodeId;
	}

	public void setTmSysErrorCodeId(Long tmSysErrorCodeId) {
		this.tmSysErrorCodeId = tmSysErrorCodeId;
	}

	public String getErrorImpact() {
		return errorImpact;
	}

	public void setErrorImpact(String errorImpact) {
		this.errorImpact = errorImpact;
	}

}
