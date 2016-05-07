package itaf.framework.merchant.dto;

// Generated Sep 2, 2014 4:45:05 PM by Hibernate Tools 3.4.0.CR1

import itaf.framework.base.dto.BaseDto;
import itaf.framework.platform.dto.SysUserDto;
import itaf.framework.position.dto.BzPositionDto;

/**
 * 
 * 用户发货地址
 * 
 * @author
 * 
 * @UpdateDate 2014年9月4日
 */
public class BzUserDeliveryAddressDto extends BaseDto {

	private static final long serialVersionUID = -1130145959859502746L;

	private Long sysUserId;
	private SysUserDto sysUserDto;
	private Long bzPositionId;
	private BzPositionDto bzPositionDto;
	private String addressTag;
	private String address;
	private String contactPerson;
	private String contactNo;
	private String postcode;

	public BzUserDeliveryAddressDto() {
	}

	public Long getSysUserId() {
		return sysUserId;
	}

	public void setSysUserId(Long sysUserId) {
		this.sysUserId = sysUserId;
	}

	public SysUserDto getSysUserDto() {
		return sysUserDto;
	}

	public void setSysUserDto(SysUserDto sysUserDto) {
		this.sysUserDto = sysUserDto;
	}

	public Long getBzPositionId() {
		return bzPositionId;
	}

	public void setBzPositionId(Long bzPositionId) {
		this.bzPositionId = bzPositionId;
	}

	public BzPositionDto getBzPositionDto() {
		return bzPositionDto;
	}

	public void setBzPositionDto(BzPositionDto bzPositionDto) {
		this.bzPositionDto = bzPositionDto;
	}

	public String getAddressTag() {
		return addressTag;
	}

	public void setAddressTag(String addressTag) {
		this.addressTag = addressTag;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

}
