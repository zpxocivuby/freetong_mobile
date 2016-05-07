package itaf.framework.merchant.dto;

// Generated Sep 2, 2014 4:45:05 PM by Hibernate Tools 3.4.0.CR1

import itaf.framework.base.dto.BaseDto;
import itaf.framework.platform.dto.SysUserDto;

import java.util.HashSet;
import java.util.Set;

/**
 * 
 * 货架
 * 
 * @author
 * 
 * @UpdateDate 2014年9月4日
 */
public class BzShelfDto extends BaseDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2854939290916808210L;
	private SysUserDto sysUserDto;
	private Long bzProductId;
	private Long stockNum;
	private Long soldNum;
	private Set<BzStockOrderItemDto> bzStockOrderItemDtos = new HashSet<BzStockOrderItemDto>(
			0);

	public BzShelfDto() {
	}

	public SysUserDto getSysUserDto() {
		return sysUserDto;
	}

	public void setSysUserDto(SysUserDto sysUserDto) {
		this.sysUserDto = sysUserDto;
	}

	public Long getBzProductId() {
		return bzProductId;
	}

	public void setBzProductId(Long bzProductId) {
		this.bzProductId = bzProductId;
	}

	public Long getStockNum() {
		return stockNum;
	}

	public void setStockNum(Long stockNum) {
		this.stockNum = stockNum;
	}

	public Long getSoldNum() {
		return soldNum;
	}

	public void setSoldNum(Long soldNum) {
		this.soldNum = soldNum;
	}

	public Set<BzStockOrderItemDto> getBzStockOrderItemDtos() {
		return bzStockOrderItemDtos;
	}

	public void setBzStockOrderItemDtos(
			Set<BzStockOrderItemDto> bzStockOrderItemDtos) {
		this.bzStockOrderItemDtos = bzStockOrderItemDtos;
	}

}
