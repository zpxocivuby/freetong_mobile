package itaf.framework.order.dto;

// Generated Sep 2, 2014 4:45:05 PM by Hibernate Tools 3.4.0.CR1

import itaf.framework.base.dto.BaseDto;
import itaf.framework.product.dto.BzProductDto;

import java.math.BigDecimal;

/**
 * 
 * 订单条目
 * 
 * @author
 * 
 * @UpdateDate 2014年9月4日
 */
public class BzOrderItemDto extends BaseDto {


	/**
	 * 
	 */
	private static final long serialVersionUID = -4796501802406620849L;
	private BzOrderDto bzOrderDto;
	private BzProductDto bzProductDto;
	private Long itemNum;
	private BigDecimal itemUnitPrice;
	private BigDecimal itemDiscount;
	private BigDecimal itemPreferential;
	private BigDecimal itemAmount;
	private Long itemStockStatus;

	public BzOrderItemDto() {
	}

	public BzOrderDto getBzOrderDto() {
		return bzOrderDto;
	}

	public void setBzOrderDto(BzOrderDto bzOrderDto) {
		this.bzOrderDto = bzOrderDto;
	}

	public BzProductDto getBzProductDto() {
		return bzProductDto;
	}

	public void setBzProductDto(BzProductDto bzProductDto) {
		this.bzProductDto = bzProductDto;
	}

	public Long getItemNum() {
		return itemNum;
	}

	public void setItemNum(Long itemNum) {
		this.itemNum = itemNum;
	}

	public BigDecimal getItemUnitPrice() {
		return itemUnitPrice;
	}

	public void setItemUnitPrice(BigDecimal itemUnitPrice) {
		this.itemUnitPrice = itemUnitPrice;
	}

	public BigDecimal getItemDiscount() {
		return itemDiscount;
	}

	public void setItemDiscount(BigDecimal itemDiscount) {
		this.itemDiscount = itemDiscount;
	}

	public BigDecimal getItemPreferential() {
		return itemPreferential;
	}

	public void setItemPreferential(BigDecimal itemPreferential) {
		this.itemPreferential = itemPreferential;
	}

	public BigDecimal getItemAmount() {
		return itemAmount;
	}

	public void setItemAmount(BigDecimal itemAmount) {
		this.itemAmount = itemAmount;
	}

	public Long getItemStockStatus() {
		return itemStockStatus;
	}

	public void setItemStockStatus(Long itemStockStatus) {
		this.itemStockStatus = itemStockStatus;
	}

}
