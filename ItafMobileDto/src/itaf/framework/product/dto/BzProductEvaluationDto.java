package itaf.framework.product.dto;

// Generated Sep 2, 2014 4:45:05 PM by Hibernate Tools 3.4.0.CR1

import itaf.framework.base.dto.BaseDto;
import itaf.framework.platform.dto.SysUserDto;

import java.util.Date;

/**
 * 
 * 商品评价
 * 
 * @author
 * 
 * @UpdateDate 2014年9月4日
 */
public class BzProductEvaluationDto extends BaseDto {


	/**
	 * 
	 */
	private static final long serialVersionUID = 648717185536844013L;
	private SysUserDto sysUserDto;
	private BzProductDto bzProductDto;
	private Long evaluateRank;
	private String evaluateContent;
	private Date evaluateTime;

	public BzProductEvaluationDto() {
	}

	public BzProductEvaluationDto(Date evaluateTime) {
		this.evaluateTime = evaluateTime;
	}

	public SysUserDto getSysUserDto() {
		return sysUserDto;
	}

	public void setSysUserDto(SysUserDto sysUserDto) {
		this.sysUserDto = sysUserDto;
	}

	public BzProductDto getBzProductDto() {
		return bzProductDto;
	}

	public void setBzProductDto(BzProductDto bzProductDto) {
		this.bzProductDto = bzProductDto;
	}

	public Long getEvaluateRank() {
		return evaluateRank;
	}

	public void setEvaluateRank(Long evaluateRank) {
		this.evaluateRank = evaluateRank;
	}

	public String getEvaluateContent() {
		return evaluateContent;
	}

	public void setEvaluateContent(String evaluateContent) {
		this.evaluateContent = evaluateContent;
	}

	public Date getEvaluateTime() {
		return evaluateTime;
	}

	public void setEvaluateTime(Date evaluateTime) {
		this.evaluateTime = evaluateTime;
	}

}
