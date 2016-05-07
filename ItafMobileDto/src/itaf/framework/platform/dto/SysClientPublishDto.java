package itaf.framework.platform.dto;

// Generated Sep 2, 2014 4:45:05 PM by Hibernate Tools 3.4.0.CR1

import itaf.framework.base.dto.BaseDto;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * 
 * 手机客户端发布信息
 *
 * @author
 *
 * @UpdateDate 2014年9月4日
 */
public class SysClientPublishDto extends BaseDto {


	/**
	 * 
	 */
	private static final long serialVersionUID = -8704413082056330741L;
	private SysClientVersionDto sysClientVersionDto;
	private String publishNo;
	private String publishStatus;
	private Long publishUser;
	private Date publishTime;
	private String filePath;
	private Set<SysAttachmentDto> sysAttachmentDtos = new HashSet<SysAttachmentDto>(0);

	public SysClientPublishDto() {
	}

	public SysClientVersionDto getSysClientVersionDto() {
		return sysClientVersionDto;
	}

	public void setSysClientVersionDto(SysClientVersionDto sysClientVersionDto) {
		this.sysClientVersionDto = sysClientVersionDto;
	}

	public String getPublishNo() {
		return publishNo;
	}

	public void setPublishNo(String publishNo) {
		this.publishNo = publishNo;
	}

	public String getPublishStatus() {
		return publishStatus;
	}

	public void setPublishStatus(String publishStatus) {
		this.publishStatus = publishStatus;
	}

	public Long getPublishUser() {
		return publishUser;
	}

	public void setPublishUser(Long publishUser) {
		this.publishUser = publishUser;
	}

	public Date getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public Set<SysAttachmentDto> getSysAttachmentDtos() {
		return sysAttachmentDtos;
	}

	public void setSysAttachmentDtos(Set<SysAttachmentDto> sysAttachmentDtos) {
		this.sysAttachmentDtos = sysAttachmentDtos;
	}


}
