package itaf.framework.certificate.dto;

// Generated Sep 2, 2014 4:45:05 PM by Hibernate Tools 3.4.0.CR1

import itaf.framework.base.dto.BaseDto;
import itaf.framework.common.dto.BzAttachmentDto;
import itaf.framework.platform.dto.SysUserDto;
import itaf.framework.workflow.dto.BzApprovalInfoDto;

import java.util.List;

/**
 * 
 * 配送服务申请单
 * 
 * @author
 * 
 * @UpdateDate 2014年9月4日
 */

public class BzApplyDistCertificateDto extends BaseDto {

	private static final long serialVersionUID = 6523676927342841072L;

	private SysUserDto sysUserDto;
	private Long bzAttachmentId;
	private BzAttachmentDto bzAttachmentDto;
	private String companyName;
	private String companyAddress;
	private Long serviceType;
	private Long serviceCoverage;
	private Long approvalStatus;
	private Long bzWorkflowId;
	private List<BzApprovalInfoDto> bzApprovalInfos;

	public BzApplyDistCertificateDto() {
		//
	}

	public SysUserDto getSysUserDto() {
		return sysUserDto;
	}

	public void setSysUserDto(SysUserDto sysUserDto) {
		this.sysUserDto = sysUserDto;
	}

	public Long getBzAttachmentId() {
		return bzAttachmentId;
	}

	public void setBzAttachmentId(Long bzAttachmentId) {
		this.bzAttachmentId = bzAttachmentId;
	}

	public BzAttachmentDto getBzAttachmentDto() {
		return bzAttachmentDto;
	}

	public void setBzAttachmentDto(BzAttachmentDto bzAttachmentDto) {
		this.bzAttachmentDto = bzAttachmentDto;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyAddress() {
		return companyAddress;
	}

	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}

	public Long getServiceType() {
		return this.serviceType;
	}

	public void setServiceType(Long serviceType) {
		this.serviceType = serviceType;
	}

	public Long getServiceCoverage() {
		return this.serviceCoverage;
	}

	public void setServiceCoverage(Long serviceCoverage) {
		this.serviceCoverage = serviceCoverage;
	}

	public Long getApprovalStatus() {
		return approvalStatus;
	}

	public void setApprovalStatus(Long approvalStatus) {
		this.approvalStatus = approvalStatus;
	}

	public Long getBzWorkflowId() {
		return bzWorkflowId;
	}

	public void setBzWorkflowId(Long bzWorkflowId) {
		this.bzWorkflowId = bzWorkflowId;
	}

	public List<BzApprovalInfoDto> getBzApprovalInfos() {
		return bzApprovalInfos;
	}

	public void setBzApprovalInfos(List<BzApprovalInfoDto> bzApprovalInfos) {
		this.bzApprovalInfos = bzApprovalInfos;
	}

}
