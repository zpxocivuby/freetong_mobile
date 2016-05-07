package itaf.framework.certificate.dto;

// Generated Sep 2, 2014 4:45:05 PM by Hibernate Tools 3.4.0.CR1

import itaf.framework.base.dto.BaseDto;

import java.util.Date;

/**
 * 
 * 实名认证
 * 
 * @author
 * 
 * @UpdateDate 2014年9月4日
 */
public class BzRealnameCertificateDto extends BaseDto {

	private static final long serialVersionUID = 4325269193632333022L;

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

	private BzApplyRealnameCertificateDto bzRealnameCertificateApplyDto;
	private Long sysUserId;
	private String name;
	private Long sex;
	private Date birthdate;
	private String mobile;
	private Long idType;
	private String idNo;

	private String realSex;
	private String realIdType;

	public BzRealnameCertificateDto() {
	}

	public BzRealnameCertificateDto(Date birthdate) {
		this.birthdate = birthdate;
	}

	public BzApplyRealnameCertificateDto getBzApplyRealnameCertificateDto() {
		return bzRealnameCertificateApplyDto;
	}

	public void setBzApplyRealnameCertificateDto(
			BzApplyRealnameCertificateDto bzRealnameCertificateApplyDto) {
		this.bzRealnameCertificateApplyDto = bzRealnameCertificateApplyDto;
	}

	public Long getSysUserId() {
		return this.sysUserId;
	}

	public void setSysUserId(Long sysUserId) {
		this.sysUserId = sysUserId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getSex() {
		return this.sex;
	}

	public void setSex(Long sex) {
		this.sex = sex;
	}

	public Date getBirthdate() {
		return this.birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Long getIdType() {
		return this.idType;
	}

	public void setIdType(Long idType) {
		this.idType = idType;
	}

	public String getIdNo() {
		return this.idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public String getRealSex() {
		realSex = SEX_MALE.equals(sex) ? MALE : FEMALE;
		return realSex;
	}

	public void setRealSex(String realSex) {
		this.realSex = realSex;
	}

	public String getRealIdType() {
		realIdType = ID_TYPE_CARD.equals(idType) ? ID_CARD : ID_PASSPORT;
		return realIdType;
	}

	public void setRealIdType(String realIdType) {
		this.realIdType = realIdType;
	}

}
