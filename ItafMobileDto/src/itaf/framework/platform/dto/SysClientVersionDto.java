package itaf.framework.platform.dto;

// Generated Sep 2, 2014 4:45:05 PM by Hibernate Tools 3.4.0.CR1

import itaf.framework.base.dto.BaseDto;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * 
 * 手机客户端版本
 *
 * @author
 *
 * @UpdateDate 2014年9月4日
 */
public class SysClientVersionDto extends BaseDto {


	/**
	 * 
	 */
	private static final long serialVersionUID = -4191490103087573339L;
	private String type;
	private String versionNo;
	private String status;
	private String statusPublish;
	private Date publishTime;
	private Set<SysClientPublishDto> sysClientPublishDtos = new HashSet<SysClientPublishDto>(
			0);

	public SysClientVersionDto() {
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getVersionNo() {
		return versionNo;
	}

	public void setVersionNo(String versionNo) {
		this.versionNo = versionNo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusPublish() {
		return statusPublish;
	}

	public void setStatusPublish(String statusPublish) {
		this.statusPublish = statusPublish;
	}

	public Date getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}

	public Set<SysClientPublishDto> getSysClientPublishDtos() {
		return sysClientPublishDtos;
	}

	public void setSysClientPublishDtos(
			Set<SysClientPublishDto> sysClientPublishDtos) {
		this.sysClientPublishDtos = sysClientPublishDtos;
	}


}
