package itaf.framework.position.dto;

// Generated Sep 2, 2014 4:45:05 PM by Hibernate Tools 3.4.0.CR1

import itaf.framework.base.dto.BaseDto;
import itaf.framework.platform.dto.SysUserDto;

/**
 * 
 * 用户位置信息
 * 
 * @author
 * 
 * @UpdateDate 2014年9月4日
 */
public class BzUserPositionDto extends BaseDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2177579146807566891L;
	private Long sysUserId;
	private SysUserDto sysUserDto;
	private Long bzPositionId;
	private BzPositionDto bzPositionDto;
	private Long serviceCoverage;

	public BzUserPositionDto() {
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

	public Long getServiceCoverage() {
		return serviceCoverage;
	}

	public void setServiceCoverage(Long serviceCoverage) {
		this.serviceCoverage = serviceCoverage;
	}

}
