package itaf.framework.certificate.dto;

// Generated Sep 2, 2014 4:45:05 PM by Hibernate Tools 3.4.0.CR1

import itaf.framework.base.dto.BaseDto;
import itaf.framework.common.dto.BzAttachmentDto;
import itaf.framework.platform.dto.SysUserDto;
import itaf.framework.workflow.dto.BzApprovalInfoDto;

import java.util.Date;
import java.util.List;

/**
 * 
 * 实名认证申请单
 * 
 * @author
 * 
 * @UpdateDate 2014年9月4日
 */
public class BzApplyRealnameCertificateDto extends BaseDto {

	private static final long serialVersionUID = -2073108652295355878L;

	// 身份证
	public static final Long ID_TYPE_CARD = 1L;
	// 护照
	public static final Long ID_TYPE_PASSPORT = 2L;
	// 身份证
	public static final String ID_CARD = "身份证";
	// 护照
	public static final String ID_PASSPORT = "护照";
	// 男性
	public static final Long SEX_MALE = 1L;
	// 女性
	public static final Long SEX_FEMALE = 2L;
	// 男性
	public static final String MALE = "男";
	// 女性
	public static final String FEMALE = "女";

	private SysUserDto sysUserDto;
	private Long bzAttachmentId;
	private BzAttachmentDto bzAttachmentDto;
	private String name;
	private Long sex;
	private Date birthdate;
	private String mobile;
	private Long idType;
	private String idNo;
	private Long approvalStatus;
	private Long bzWorkflowId;
	private List<BzApprovalInfoDto> bzApprovalInfos;

	public BzApplyRealnameCertificateDto() {
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getSex() {
		return sex;
	}

	public void setSex(Long sex) {
		this.sex = sex;
	}

	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Long getIdType() {
		return idType;
	}

	public void setIdType(Long idType) {
		this.idType = idType;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
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
