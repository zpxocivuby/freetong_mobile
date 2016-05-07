package itaf.framework.cart.dto;

// Generated Sep 2, 2014 4:45:05 PM by Hibernate Tools 3.4.0.CR1

import itaf.framework.base.dto.BaseDto;
import itaf.framework.platform.dto.SysUserDto;
import itaf.framework.product.dto.BzProductDto;

import java.math.BigDecimal;

/**
 * 
 * 购物车条目临时
 * 
 * @author
 * 
 * @UpdateDate 2014年9月4日
 */

public class BzCartItemTempDto extends BaseDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3072844374034035391L;
	/**
	 * 
	 */

	private SysUserDto sysUserDto;
	private BzProductDto bzProductDto;
	private Long itemNum;
	private BigDecimal itemUnitPrice;
	private BigDecimal itemDiscount;
	private BigDecimal itemPreferential;
	private BigDecimal itemPrice;

	public BzCartItemTempDto() {
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

	public Long getItemNum() {
		return this.itemNum;
	}

	public void setItemNum(Long itemNum) {
		this.itemNum = itemNum;
	}

	public BigDecimal getItemUnitPrice() {
		return this.itemUnitPrice;
	}

	public void setItemUnitPrice(BigDecimal itemUnitPrice) {
		this.itemUnitPrice = itemUnitPrice;
	}

	public BigDecimal getItemDiscount() {
		return this.itemDiscount;
	}

	public void setItemDiscount(BigDecimal itemDiscount) {
		this.itemDiscount = itemDiscount;
	}

	public BigDecimal getItemPreferential() {
		return this.itemPreferential;
	}

	public void setItemPreferential(BigDecimal itemPreferential) {
		this.itemPreferential = itemPreferential;
	}

	public BigDecimal getItemPrice() {
		return this.itemPrice;
	}

	public void setItemPrice(BigDecimal itemPrice) {
		this.itemPrice = itemPrice;
	}

}
